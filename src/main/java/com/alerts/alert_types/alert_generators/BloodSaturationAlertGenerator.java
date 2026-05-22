package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.BloodSaturationAlert;
import com.alerts.alert_types.NoAlert;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;
import com.alerts.alert_types.alert_factories.BloodOxygenAlertFactory;

public class BloodSaturationAlertGenerator implements AlertGeneratorStrategy {

    BloodOxygenAlertFactory factory = new BloodOxygenAlertFactory();
    public Alert evaluateData(Patient patient, List<PatientRecord> records) {
        return checkSaturation(patient, records);
    }

    private Alert checkSaturation(Patient patient, List<PatientRecord> records) {
        if  (records.isEmpty()) {
            return new NoAlert();
        }

        double latestSaturation = records.get(records.size() - 1).getMeasurementValue();
        if (latestSaturation < 92) {
            return factory.createAlert(patient.getPatientId(),
                    "Low Saturation",
                    System.currentTimeMillis(),
                    latestSaturation);
        }

        double tenMinutes = 10 * 60 * 1000L;
        for (int i = 0; i < records.size(); i++) {
            for (PatientRecord record : records) {
                double firstTime = records.get(i).getTimestamp();
                double secondTime = record.getTimestamp();
                double timeDifference = secondTime - firstTime;
                if (timeDifference > tenMinutes) {
                    break;
                }
                double firstValue = records.get(i).getMeasurementValue();
                double secondValue = record.getMeasurementValue();
                double dropValue = firstValue - secondValue;
                if (dropValue >= 5) {
                    return factory.createAlert(
                            patient.getPatientId(),
                            "Rapid Saturation Drop",
                            System.currentTimeMillis(),
                            firstValue);
                }
            }
        }

        return new NoAlert();
    }
}
