package Alerts.AlertGenerators;

import com.alerts.Alert;
import com.alerts.HypotensiveHypoxemiaAlert.HypotensiveHypoxemiaAlertGenerator;
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
}
