package com.alerts.BloodSaturationAlert;

import com.alerts.Alert;
import com.alerts.NoAlert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodSaturationAlertGenerator {

    public Alert evaluateData(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> saturationRecords = new ArrayList<>();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodSaturation")) {
                saturationRecords.add(record);
            }
        }
        return checkSaturation(patient, saturationRecords);
    }

    private Alert checkSaturation(Patient patient, List<PatientRecord> records) {
        if  (records.isEmpty()) {
            return new NoAlert();
        }

        double latestSaturation = records.get(records.size() - 1).getMeasurementValue();
        if (latestSaturation < 92) {
            return new BloodSaturationAlert(patient.getPatientId(),
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
                    return new BloodSaturationAlert(
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
