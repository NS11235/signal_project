package Alerts.AlertGenerators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.alert_generators.BloodPressureAlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BloodPressureAlertGeneratorTest {

    @Test
    void testSystolicTooHighTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(185, "SystolicPressure", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("Systolic Critical Threshold");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testSystolicTooLowTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(85, "SystolicPressure", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("Systolic Critical Threshold");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testDiastolicTooHighTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(125, "DiastolicPressure", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("Diastolic Critical Threshold");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testDiastolicTooLowTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(55, "DiastolicPressure", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("Diastolic Critical Threshold");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testSystolicIncreasingTrendTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(110, "SystolicPressure", now - 2000);
        patient.addRecord(125, "SystolicPressure", now - 1000);
        patient.addRecord(140, "SystolicPressure", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("SystolicPressure Trend Increasing");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testSystolicDecreasingTrendTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        // Each consecutive reading drops by more than 10
        patient.addRecord(140, "SystolicPressure", now - 2000);
        patient.addRecord(125, "SystolicPressure", now - 1000);
        patient.addRecord(110, "SystolicPressure", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("SystolicPressure Trend Decreasing");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testDiastolicIncreasingTrendTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(70, "DiastolicPressure", now - 2000);
        patient.addRecord(85, "DiastolicPressure", now - 1000);
        patient.addRecord(100, "DiastolicPressure", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("DiastolicPressure Trend Increasing");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testDiastolicDecreasingTrendTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(100, "DiastolicPressure", now - 2000);
        patient.addRecord(85, "DiastolicPressure", now - 1000);
        patient.addRecord(70, "DiastolicPressure", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("DiastolicPressure Trend Decreasing");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testNormalReadingsProduceNoAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(120, "SystolicPressure", now - 2000);
        patient.addRecord(122, "SystolicPressure", now - 1000);
        patient.addRecord(121, "SystolicPressure", now);
        patient.addRecord(80, "DiastolicPressure", now - 2000);
        patient.addRecord(81, "DiastolicPressure", now - 1000);
        patient.addRecord(80, "DiastolicPressure", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testEmptyRecordsProduceNoAlert() {
        Patient patient = new Patient(1);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodPressureAlertGenerator generator = new BloodPressureAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
    }
}