package data_management;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PatientTest {

    @Test
    void testAddRecordAndRetrieveWithinRange() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(72.0, "HeartRate", now);

        List<PatientRecord> records = patient.getRecords(0, now + 1000);

        assert records.size() == 1;
        assert records.get(0).getMeasurementValue() == 72.0;
        assert records.get(0).getRecordType().equals("HeartRate");
        assert records.get(0).getPatientId() == 1;
    }

    @Test
    void testGetRecordsReturnsEmptyListWhenNoRecordsAdded() {
        Patient patient = new Patient(2);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());

        assert records.isEmpty();
    }

    @Test
    void testGetRecordsExcludesRecordsBelowStartTime() {
        Patient patient = new Patient(3);
        long now = System.currentTimeMillis();
        patient.addRecord(80.0, "HeartRate", now - 10_000);
        patient.addRecord(85.0, "HeartRate", now);

        List<PatientRecord> records = patient.getRecords(now - 5_000, now + 1_000);

        assert records.size() == 1;
        assert records.get(0).getMeasurementValue() == 85.0;
    }

    @Test
    void testGetRecordsExcludesRecordsAboveEndTime() {
        Patient patient = new Patient(4);
        long now = System.currentTimeMillis();
        patient.addRecord(90.0, "HeartRate", now + 10_000); // future – outside range
        patient.addRecord(75.0, "HeartRate", now - 1_000);  // past – inside range

        List<PatientRecord> records = patient.getRecords(0, now);

        assert records.size() == 1;
        assert records.get(0).getMeasurementValue() == 75.0;
    }

    @Test
    void testGetRecordsIncludesRecordExactlyAtStartTime() {
        Patient patient = new Patient(5);
        long startTime = 1_000_000L;
        patient.addRecord(60.0, "BloodPressure", startTime);

        List<PatientRecord> records = patient.getRecords(startTime, startTime + 5_000);

        assert records.size() == 1;
        assert records.get(0).getMeasurementValue() == 60.0;
    }

    @Test
    void testGetRecordsIncludesRecordExactlyAtEndTime() {
        Patient patient = new Patient(6);
        long endTime = 2_000_000L;
        patient.addRecord(120.0, "BloodPressure", endTime);

        List<PatientRecord> records = patient.getRecords(endTime - 5_000, endTime);

        assert records.size() == 1;
        assert records.get(0).getMeasurementValue() == 120.0;
    }

    @Test
    void testGetRecordsReturnsAllRecordsInRange() {
        Patient patient = new Patient(7);
        long now = System.currentTimeMillis();
        patient.addRecord(70.0, "HeartRate", now - 3_000);
        patient.addRecord(71.0, "HeartRate", now - 2_000);
        patient.addRecord(72.0, "HeartRate", now - 1_000);

        List<PatientRecord> records = patient.getRecords(0, now);

        assert records.size() == 3;
    }

    @Test
    void testGetRecordsFiltersCorrectlyWithMixedTimestamps() {
        Patient patient = new Patient(8);
        long now = System.currentTimeMillis();
        patient.addRecord(50.0, "HeartRate", now - 20_000); // outside
        patient.addRecord(55.0, "HeartRate", now - 8_000);  // inside
        patient.addRecord(60.0, "HeartRate", now - 4_000);  // inside
        patient.addRecord(65.0, "HeartRate", now + 5_000);  // outside

        List<PatientRecord> records = patient.getRecords(now - 10_000, now);

        assert records.size() == 2;
    }

    @Test
    void testGetPatientIdReturnsCorrectId() {
        Patient patient = new Patient(42);

        assert patient.getPatientId() == 42;
    }

    @Test
    void testGetPatientIdIsPreservedAfterAddingRecords() {
        Patient patient = new Patient(99);
        long now = System.currentTimeMillis();
        patient.addRecord(100.0, "BloodSaturation", now);

        assert patient.getPatientId() == 99;
    }

    @Test
    void testRecordTypeIsStoredCorrectly() {
        Patient patient = new Patient(10);
        long now = System.currentTimeMillis();
        patient.addRecord(98.6, "Temperature", now);

        List<PatientRecord> records = patient.getRecords(0, now + 1_000);

        assert records.get(0).getRecordType().equals("Temperature");
    }

    @Test
    void testMultipleDifferentRecordTypesAreAllReturned() {
        Patient patient = new Patient(11);
        long now = System.currentTimeMillis();
        patient.addRecord(72.0,  "HeartRate",      now - 2_000);
        patient.addRecord(120.0, "BloodPressure",  now - 1_000);
        patient.addRecord(98.0,  "BloodSaturation", now);

        List<PatientRecord> records = patient.getRecords(0, now + 1_000);

        assert records.size() == 3;
    }
}
