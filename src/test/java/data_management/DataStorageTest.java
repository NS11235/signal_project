package data_management;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DataStorageTest {

    private void resetSingleton() throws Exception {
        java.lang.reflect.Field field = DataStorage.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    void testGetInstanceReturnsSameObject() throws Exception {
        resetSingleton();
        DataStorage first  = DataStorage.getInstance();
        DataStorage second = DataStorage.getInstance();

        assert first == second;
    }

    @Test
    void testAddPatientDataAndRetrieveRecord() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();
        long now = System.currentTimeMillis();
        storage.addPatientData(1, 72.0, "HeartRate", now);

        List<PatientRecord> records = storage.getRecords(1, 0, now + 1_000);

        assert records.size() == 1;
        assert records.get(0).getMeasurementValue() == 72.0;
        assert records.get(0).getRecordType().equals("HeartRate");
        assert records.get(0).getPatientId() == 1;
    }

    @Test
    void testAddMultipleRecordsForSamePatient() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();
        long now = System.currentTimeMillis();
        storage.addPatientData(1, 70.0, "HeartRate", now - 2_000);
        storage.addPatientData(1, 75.0, "HeartRate", now - 1_000);
        storage.addPatientData(1, 80.0, "HeartRate", now);

        List<PatientRecord> records = storage.getRecords(1, 0, now + 1_000);

        assert records.size() == 3;
    }

    @Test
    void testAddRecordsForDifferentPatientsAreStoredSeparately() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();
        long now = System.currentTimeMillis();
        storage.addPatientData(1, 60.0, "HeartRate", now);
        storage.addPatientData(2, 90.0, "HeartRate", now);

        List<PatientRecord> patient1Records = storage.getRecords(1, 0, now + 1_000);
        List<PatientRecord> patient2Records = storage.getRecords(2, 0, now + 1_000);

        assert patient1Records.size() == 1;
        assert patient1Records.get(0).getMeasurementValue() == 60.0;
        assert patient2Records.size() == 1;
        assert patient2Records.get(0).getMeasurementValue() == 90.0;
    }

    @Test
    void testGetRecordsReturnsEmptyListForUnknownPatient() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();

        List<PatientRecord> records = storage.getRecords(999, 0, System.currentTimeMillis());

        assert records.isEmpty();
    }

    @Test
    void testGetRecordsRespectsTimeRangeBounds() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();
        long now = System.currentTimeMillis();
        storage.addPatientData(1, 55.0, "HeartRate", now - 20_000); // outside
        storage.addPatientData(1, 60.0, "HeartRate", now - 5_000);  // inside
        storage.addPatientData(1, 65.0, "HeartRate", now + 10_000); // outside

        List<PatientRecord> records = storage.getRecords(1, now - 10_000, now);

        assert records.size() == 1;
        assert records.get(0).getMeasurementValue() == 60.0;
    }

    @Test
    void testGetAllPatientsReturnsEmptyListInitially() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();

        List<Patient> patients = storage.getAllPatients();

        assert patients.isEmpty();
    }

    @Test
    void testGetAllPatientsReturnsOnePatientAfterOneAdded() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 72.0, "HeartRate", System.currentTimeMillis());

        List<Patient> patients = storage.getAllPatients();

        assert patients.size() == 1;
        assert patients.get(0).getPatientId() == 1;
    }

    @Test
    void testGetAllPatientsReturnsAllDistinctPatients() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();
        long now = System.currentTimeMillis();
        storage.addPatientData(1, 72.0, "HeartRate", now);
        storage.addPatientData(2, 80.0, "HeartRate", now);
        storage.addPatientData(3, 95.0, "BloodSaturation", now);

        List<Patient> patients = storage.getAllPatients();

        assert patients.size() == 3;
    }

    @Test
    void testAddingMultipleRecordsForSamePatientDoesNotDuplicatePatient() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();
        long now = System.currentTimeMillis();
        storage.addPatientData(1, 70.0, "HeartRate", now - 1_000);
        storage.addPatientData(1, 75.0, "HeartRate", now);

        List<Patient> patients = storage.getAllPatients();

        assert patients.size() == 1;
    }

    @Test
    void testStoredPatientHasCorrectId() throws Exception {
        resetSingleton();
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(7, 100.0, "BloodPressure", System.currentTimeMillis());

        List<Patient> patients = storage.getAllPatients();

        assert patients.get(0).getPatientId() == 7;
    }
}
