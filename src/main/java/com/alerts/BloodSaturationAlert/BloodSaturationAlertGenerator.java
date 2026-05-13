package com.alerts.BloodSaturationAlert;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodSaturationAlertGenerator {

    public void evaluateData(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> saturationRecords = new ArrayList<>();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodSaturation")) {
                saturationRecords.add(record);
            }
        }
    }

    private void checkSaturation(Patient patient, List<PatientRecord> records) {
        if  (records.isEmpty()) {
            return;
        }

        double latestSaturation = records.get(records.size() - 1).getMeasurementValue();
        if (latestSaturation < 92) {
            triggerAlert(new BloodSaturationAlert(patient.getPatientId(), "Low Saturation", System.currentTimeMillis(), latestSaturation));
        }

        double tenMinutes = 10 * 60 * 1000L;
        for (int i = 0; i < records.size(); i++) {
            for (int j = 0; j < records.size(); j++) {
                double firstTime = records.get(i).getTimestamp();
                double secondTime = records.get(j).getTimestamp();
                double timeDifference = secondTime - firstTime;
                if (timeDifference > tenMinutes) {
                    break;
                }
                double firstValue = records.get(i).getMeasurementValue();
                double secondValue = records.get(j).getMeasurementValue();
                double dropValue = firstValue - secondValue;
                if (dropValue >= 5) {
                    triggerAlert(new BloodSaturationAlert(patient.getPatientId(), "Rapid Saturation Drop", System.currentTimeMillis(), firstValue));
                    return;
                }
            }
        }
    }

    private void triggerAlert(Alert alert) {

    }
}
