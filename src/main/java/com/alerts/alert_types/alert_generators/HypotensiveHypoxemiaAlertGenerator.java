package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.HypotensiveHypoxemiaAlert;
import com.alerts.alert_types.NoAlert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class HypotensiveHypoxemiaAlertGenerator implements AlertGeneratorStrategy {

    public Alert evaluateData(Patient patient, List<PatientRecord> records) {
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
            return new HypotensiveHypoxemiaAlert(
                    String.valueOf(patient.getPatientId()),
                    "Low Systolic Pressure and Blood Saturation, Hypotensive Hypoxemia Alert",
                    System.currentTimeMillis()
            );
        }

        return new NoAlert();
    }
}
