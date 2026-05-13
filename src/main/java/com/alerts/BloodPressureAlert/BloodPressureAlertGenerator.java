package com.alerts.BloodPressureAlert;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureAlertGenerator {

    public void evaluateData(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> systolicRecords = new ArrayList<>();
        List<PatientRecord> diastolicRecords = new ArrayList<>();
        for  (PatientRecord record : records) {
            if (record.getRecordType().contains("SystolicPressure")) {
                systolicRecords.add(record);
            } else if (record.getRecordType().contains("DiastolicPressure")) {
                diastolicRecords.add(record);
            }
        }

        checkTrend(patient, systolicRecords, "SystolicPressure");
        checkTrend(patient, diastolicRecords, "DiastolicPressure");

        if (!systolicRecords.isEmpty()) {
            double latestSystolic = systolicRecords.get(systolicRecords.size() - 1).getMeasurementValue();
            if (latestSystolic > 180 || latestSystolic < 90) {
                triggerAlert(new BloodPressureAlert(patient.getPatientId(), "Systolic Critical Threshold", System.currentTimeMillis(), latestSystolic));
            }
        }
        if (!diastolicRecords.isEmpty()) {
            double latestDiastolic =  diastolicRecords.get(diastolicRecords.size() - 1).getMeasurementValue();
            if (latestDiastolic > 120 || latestDiastolic < 60) {
                triggerAlert(new BloodPressureAlert(patient.getPatientId(), "Diastolic Critical Threshold", System.currentTimeMillis(), latestDiastolic));
            }
        }
    }

    private void checkTrend(Patient patient, List<PatientRecord> records, String type) {
        if (records.size() < 3) {
            return;
        }

        double firstMeasure = records.get(records.size()-  3).getMeasurementValue();
        double secondMeasure = records.get(records.size()-  2).getMeasurementValue();
        double thirdMeasure = records.get(records.size()-  1).getMeasurementValue();

        boolean isIncreasing = ((secondMeasure - firstMeasure) > 0) && (thirdMeasure - secondMeasure > 0);
        boolean isDecreasing = ((firstMeasure - secondMeasure) > 0) && (secondMeasure - thirdMeasure > 0);

        if (isIncreasing) {
            String condition = type + " Trend Increasing";
            triggerAlert(new BloodPressureAlert(patient.getPatientId(), condition, System.currentTimeMillis(), thirdMeasure));
        } else if (isDecreasing) {
            String condition = type + " Trend Decreasing";
            triggerAlert(new BloodPressureAlert(patient.getPatientId(), condition, System.currentTimeMillis(), thirdMeasure));
        }

    }

    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
    }
}
