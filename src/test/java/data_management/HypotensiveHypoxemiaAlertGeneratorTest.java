package data_management;

import com.alerts.HypotensiveHypoxemiaAlert.HypotensiveHypoxemiaAlert;
import com.alerts.HypotensiveHypoxemiaAlert.HypotensiveHypoxemiaAlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HypotensiveHypoxemiaAlertGeneratorTest {
    @Test
    void testAlertGeneratorConditionsMet() {
        Patient patient = new Patient(1);
        patient.addRecord(89, "SystolicPressure", System.currentTimeMillis());
        patient.addRecord(91, "BloodSaturation", System.currentTimeMillis());

        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());
        HypotensiveHypoxemiaAlertGenerator generator = new HypotensiveHypoxemiaAlertGenerator();
        generator.evaluateData(patient, records);
    }
}
