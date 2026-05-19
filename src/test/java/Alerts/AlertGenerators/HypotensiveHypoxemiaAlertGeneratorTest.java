package Alerts.AlertGenerators;

import com.alerts.HypotensiveHypoxemiaAlert.HypotensiveHypoxemiaAlertGenerator;
import com.alerts.alert_outputs.AlertOutputStrategy;
import com.alerts.alert_outputs.ConsoleAlertOutputStrategy;
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
        AlertOutputStrategy outputStrategy = new ConsoleAlertOutputStrategy();
        HypotensiveHypoxemiaAlertGenerator generator = new HypotensiveHypoxemiaAlertGenerator(outputStrategy);
        generator.evaluateData(patient, records);
    }
}
