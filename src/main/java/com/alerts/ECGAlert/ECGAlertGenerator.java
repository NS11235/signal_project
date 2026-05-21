package com.alerts.ECGAlert;

import com.alerts.alert_outputs.AlertOutputStrategy;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class ECGAlertGenerator {
    private static final int SLIDING_WINDOW_SIZE = 10;
    private static final double OUTLIER_PEAK_SIZE = 2.0;

    private final AlertOutputStrategy alertOutputStrategy;

    public ECGAlertGenerator(AlertOutputStrategy alertOutputStrategy) {
        this.alertOutputStrategy = alertOutputStrategy;
    }

    public void evaluateData(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> ecgRecords = new ArrayList<>();
        for (PatientRecord patientRecord : records) {
            if (patientRecord.getRecordType().equals("ECG")) {
                ecgRecords.add(patientRecord);
            }
        }

        // Checking that we have enough data
        if (ecgRecords.size() < SLIDING_WINDOW_SIZE) {
            return;
        }

        int previousIndex = records.size() - 1;
        double newestValue = records.get(previousIndex).getMeasurementValue();

        double average = windowAverage(ecgRecords, previousIndex, newestValue);

        if (average > 0 && Math.abs(previousIndex) > OUTLIER_PEAK_SIZE * average) {
            triggerAlert(new ECGAlert(
                    String.valueOf(patient.getPatientId()),
                    "Abnormal ECG Peak",
                    ecgRecords.get(previousIndex).getTimestamp()
            ));
        }
    }

    private double windowAverage(List<PatientRecord> records, int previousIndex, double newestValue) {
        double sum = 0;
        for (int i = previousIndex - SLIDING_WINDOW_SIZE; i < previousIndex; i++) {
            sum += Math.abs(records.get(i).getMeasurementValue());
        }
        return sum / SLIDING_WINDOW_SIZE;
    }

    private void triggerAlert(ECGAlert alert) {
        alertOutputStrategy.output(alert);
    }
}
