package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.NoAlert;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;
import com.alerts.alert_types.alert_factories.ECGAlertFactory;

/**
 * Generates alerts related to ECG measurements.
 * This class detects abnormal ECG peaks by comparing
 * the latest measurement to the average of previous values.
 */
public class ECGAlertGenerator implements AlertGeneratorStrategy {
    private static final int SLIDING_WINDOW_SIZE = 10;
    private static final double OUTLIER_PEAK_SIZE = 2.0;

    ECGAlertFactory factory = new ECGAlertFactory();

    /**
     * Evaluates ECG records and generates alerts if needed.
     *
     * @param patient the patient being evaluated
     * @param records the patient records to evaluate
     * @return an alert if an abnormal ECG peak is detected,
     * otherwise a no-alert object
     */
    public Alert evaluateData(Patient patient, List<PatientRecord> records) {
        if (records.size() <= SLIDING_WINDOW_SIZE) {
            return new NoAlert();
        }

        int previousIndex = records.size() - 1;

        double average = windowAverage(records, previousIndex);

        if (average > 0 && Math.abs(records.get(previousIndex).getMeasurementValue()) > OUTLIER_PEAK_SIZE * average) {
            return factory.createAlert(
                    patient.getPatientId(),
                    "Abnormal ECG Peak",
                    records.get(previousIndex).getTimestamp(),
                    0.0
            );
        }

        return new NoAlert();
    }

    /**
     * Calculates the average measurement value within
     * the sliding window.
     *
     * @param records the list of patient records
     * @param previousIndex the index of the latest record
     * @return the average measurement value of the window
     */
    private double windowAverage(List<PatientRecord> records, int previousIndex) {
        double sum = 0;
        for (int i = previousIndex - SLIDING_WINDOW_SIZE; i < previousIndex; i++) {
            sum += Math.abs(records.get(i).getMeasurementValue());
        }
        return sum / SLIDING_WINDOW_SIZE;
    }
}
