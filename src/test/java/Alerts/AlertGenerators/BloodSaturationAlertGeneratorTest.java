package Alerts.AlertGenerators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.alert_generators.BloodSaturationAlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BloodSaturationAlertGeneratorTest {

    @Test
    void testLowSaturationTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(91, "BloodSaturation", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodSaturationAlertGenerator generator = new BloodSaturationAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("Low Saturation");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testSaturationExactlyAtThresholdProducesNoAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(92, "BloodSaturation", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodSaturationAlertGenerator generator = new BloodSaturationAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
    }

    @Test
    void testRapidDropWithinTenMinutesTriggersAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(97, "BloodSaturation", now - (5 * 60 * 1000)); // 5 min ago
        patient.addRecord(92, "BloodSaturation", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodSaturationAlertGenerator generator = new BloodSaturationAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == patient.getPatientId();
        assert alert.getCondition().equals("Rapid Saturation Drop");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testRapidDropOutsideTenMinutesProducesNoAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(97, "BloodSaturation", now - (15 * 60 * 1000)); // 15 min ago
        patient.addRecord(92, "BloodSaturation", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodSaturationAlertGenerator generator = new BloodSaturationAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
    }

    @Test
    void testDropLessThanFivePointsProducesNoAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(96, "BloodSaturation", now - (2 * 60 * 1000));
        patient.addRecord(93, "BloodSaturation", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodSaturationAlertGenerator generator = new BloodSaturationAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
    }

    @Test
    void testNormalSaturationProducesNoAlert() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(98, "BloodSaturation", now - 1000);
        patient.addRecord(97, "BloodSaturation", now);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodSaturationAlertGenerator generator = new BloodSaturationAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testEmptyRecordsProduceNoAlert() {
        Patient patient = new Patient(1);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        BloodSaturationAlertGenerator generator = new BloodSaturationAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);

        assert alert.getPatientId() == 0;
        assert alert.getCondition().equals("no condition");
    }
}
