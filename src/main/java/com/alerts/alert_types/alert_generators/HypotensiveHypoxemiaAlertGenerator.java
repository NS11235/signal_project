package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.NoAlert;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.alert_types.alert_factories.HypotensiveHypoxemiaAlertFactory;
import java.util.List;

/**
 * Generates alerts related to hypotensive hypoxemia conditions.
 * This class checks whether low systolic blood pressure and
 * low blood oxygen saturation occur within a short period.
 */
public class HypotensiveHypoxemiaAlertGenerator implements AlertGeneratorStrategy {

    /**
     * Evaluates patient records and generates a hypotensive
     * hypoxemia alert if conditions are met.
     *
     * @param patient the patient being evaluated
     * @param records the patient records to evaluate
     * @return an alert if conditions are met,
     * otherwise a no-alert object
     */
    public Alert evaluateData(Patient patient, List<PatientRecord> records) {
        HypotensiveHypoxemiaAlertFactory factory = new HypotensiveHypoxemiaAlertFactory();
        PatientRecord latestSystolic = null;
        PatientRecord latestSaturation = null;

        // Records latest records of systolic and saturation alerts
        for (PatientRecord record : records) {
            if (record.getRecordType().contains("SystolicPressure")) {
                latestSystolic = record;
            } else if (record.getRecordType().equals("BloodSaturation")) {
                latestSaturation = record;
            }
        }

        // Patient could have no systolic or saturation alerts, making this alert impossible
        if (latestSystolic == null || latestSaturation == null) {
            return new NoAlert();
        }

        // Calculates time difference for alerts to tripper Hypotensive Hypoxemia alert
        long timeDiff = Math.abs(latestSystolic.getTimestamp() - latestSaturation.getTimestamp());
        long tenMinutes = 10 * 60 * 1000L;

        // If both are under the threshold at the same time, triggers alert
        if (timeDiff < tenMinutes
                && latestSystolic.getMeasurementValue() < 90
                && latestSaturation.getMeasurementValue() < 92) {
            return factory.createAlert(
                    patient.getPatientId(),
                    "Low Systolic Pressure and Blood Saturation, Hypotensive Hypoxemia Alert",
                    System.currentTimeMillis(),
                    0.0
            );
        }

        return new NoAlert();
    }
}
