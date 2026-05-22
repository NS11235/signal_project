package Alerts.AlertGenerators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.alert_generators.ECGAlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ECGAlertGeneratorTest {

    private void addECGRecords(Patient patient, int count, double value, long baseTime) {
        for (int i = 0; i < count; i++) {
            patient.addRecord(value, "ECG", baseTime + (i * 100L));
        }
    }


    @Test
    void testAbnormalPeakTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();

        addECGRecords(patient, 10, 1.0, now - 11000);
        patient.addRecord(5.0, "ECG", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        ECGAlertGenerator generator = new ECGAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("Abnormal ECG Peak");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testNormalReadingsProduceNoAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        addECGRecords(patient, 11, 1.0, now - 11000);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        ECGAlertGenerator generator = new ECGAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testFewerThanWindowSizeRecordsProduceNoAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        addECGRecords(patient, 5, 1.0, now - 5000);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        ECGAlertGenerator generator = new ECGAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
    }

    @Test
    void testEmptyRecordsProduceNoAlert() {
        Patient patient = new Patient(1);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        ECGAlertGenerator generator = new ECGAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
    }

    @Test
    void testExactlyWindowSizeRecordsProduceNoAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        addECGRecords(patient, 10, 1.0, now - 10000);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        ECGAlertGenerator generator = new ECGAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
    }
}
