package Alerts.AlertGenerators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.alert_generators.HypotensiveHypoxemiaAlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HypotensiveHypoxemiaAlertGeneratorTest {
    @Test
    void testAlertGeneratorConditionsMet() {
        Patient patient = new Patient(1);
        long currentTime = System.currentTimeMillis();
        patient.addRecord(89, "SystolicPressure", currentTime);
        patient.addRecord(91, "BloodSaturation", currentTime);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        HypotensiveHypoxemiaAlertGenerator generator = new HypotensiveHypoxemiaAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);
        assert Integer.parseInt(alert.getPatientId()) == patient.getPatientId();
        assert alert.getCondition().equals("Low Systolic Pressure and Blood Saturation, Hypotensive Hypoxemia Alert");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testSystolicConditionMet() {
        Patient patient = new Patient(1);
        long currentTime = System.currentTimeMillis();
        patient.addRecord(89, "SystolicPressure", currentTime);
        patient.addRecord(100, "BloodSaturation", currentTime);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        HypotensiveHypoxemiaAlertGenerator generator = new HypotensiveHypoxemiaAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);
        assert Integer.parseInt(alert.getPatientId()) == 0;
        assert alert.getCondition().equals("no condition");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testBloodSaturationConditionMet() {
        Patient patient = new Patient(1);
        long currentTime = System.currentTimeMillis();
        patient.addRecord(100, "SystolicPressure", currentTime);
        patient.addRecord(91, "BloodSaturation", currentTime);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        HypotensiveHypoxemiaAlertGenerator generator = new HypotensiveHypoxemiaAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);
        assert Integer.parseInt(alert.getPatientId()) == 0;
        assert alert.getCondition().equals("no condition");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }

    @Test
    void testNoConditionsMet() {
        Patient patient = new Patient(1);
        long currentTime = System.currentTimeMillis();
        patient.addRecord(100, "SystolicPressure", currentTime);
        patient.addRecord(100, "BloodSaturation", currentTime);

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        HypotensiveHypoxemiaAlertGenerator generator = new HypotensiveHypoxemiaAlertGenerator();
        Alert alert = generator.evaluateData(patient, records);
        assert Integer.parseInt(alert.getPatientId()) == 0;
        assert alert.getCondition().equals("no condition");
        assert alert.getTimestamp() <= System.currentTimeMillis();
    }
}
