package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.NoAlert;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;
import com.alerts.alert_types.alert_factories.BloodOxygenAlertFactory;

/**
 * Generates alerts related to blood oxygen saturation values.
 * This class checks for low saturation values and rapid drops.
 */
public class BloodSaturationAlertGenerator implements AlertGeneratorStrategy {

    BloodOxygenAlertFactory factory = new BloodOxygenAlertFactory();

    /**
     * Evaluates blood oxygen records and generates alerts if needed.
     *
     * @param patient the patient being evaluated
     * @param records the patient records to evaluate
     * @return an alert if conditions are met, otherwise a no-alert object
     */
    public Alert evaluateData(Patient patient, List<PatientRecord> records) {
        return checkSaturation(patient, records);
    }

    /**
     * Checks blood oxygen records for low values
     * or rapid decreases in saturation.
     *
     * @param patient the patient being evaluated
     * @param records the blood oxygen records
     * @return an alert if conditions are met, otherwise a no-alert object
     */
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
