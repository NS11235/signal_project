package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.BloodPressureAlert;
import com.alerts.alert_types.NoAlert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureAlertGenerator implements AlertGeneratorStrategy {

    public Alert evaluateData(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> systolicRecords = new ArrayList<>();
        List<PatientRecord> diastolicRecords = new ArrayList<>();
        for  (PatientRecord record : records) {
            if (record.getRecordType().contains("SystolicPressure")) {
                systolicRecords.add(record);
            } else if (record.getRecordType().contains("DiastolicPressure")) {
                diastolicRecords.add(record);
            }
        }

        Alert trendAlert1 = checkTrend(patient, systolicRecords, "SystolicPressure");
        Alert trendAlert2 = checkTrend(patient, diastolicRecords, "DiastolicPressure");

        if (trendAlert1 instanceof BloodPressureAlert) {
            return trendAlert1;
        } else if (trendAlert2 instanceof BloodPressureAlert) {
            return trendAlert2;
        }

        if (!systolicRecords.isEmpty()) {
            double latestSystolic = systolicRecords.get(systolicRecords.size() - 1).getMeasurementValue();
            if (latestSystolic > 180 || latestSystolic < 90) {
                return new BloodPressureAlert(
                        patient.getPatientId(),
                        "Systolic Critical Threshold",
                        System.currentTimeMillis(),
                        latestSystolic);
            }
        }
        if (!diastolicRecords.isEmpty()) {
            double latestDiastolic =  diastolicRecords.get(diastolicRecords.size() - 1).getMeasurementValue();
            if (latestDiastolic > 120 || latestDiastolic < 60) {
                return new BloodPressureAlert(
                        patient.getPatientId(),
                        "Diastolic Critical Threshold",
                        System.currentTimeMillis(),
                        latestDiastolic);
            }
        }

        return new NoAlert();
    }


    // TODO: Split into separate alert type
    private Alert checkTrend(Patient patient, List<PatientRecord> records, String type) {
        if (records.size() < 3) {
            return new NoAlert();
        }

        double firstMeasure = records.get(records.size()-  3).getMeasurementValue();
        double secondMeasure = records.get(records.size()-  2).getMeasurementValue();
        double thirdMeasure = records.get(records.size()-  1).getMeasurementValue();

        boolean isIncreasing = ((secondMeasure - firstMeasure) > 10) && (thirdMeasure - secondMeasure > 10);
        boolean isDecreasing = ((firstMeasure - secondMeasure) > 10) && (secondMeasure - thirdMeasure > 10);

        if (isIncreasing) {
            String condition = type + " Trend Increasing";
            return new BloodPressureAlert(
                    patient.getPatientId(),
                    condition,
                    System.currentTimeMillis(),
                    thirdMeasure);
        } else if (isDecreasing) {
            String condition = type + " Trend Decreasing";
            return new BloodPressureAlert(
                    patient.getPatientId(),
                    condition,
                    System.currentTimeMillis(),
                    thirdMeasure);
        }


        return new NoAlert();
    }
}
