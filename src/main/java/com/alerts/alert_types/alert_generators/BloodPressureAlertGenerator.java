package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.BloodPressureAlert;
import com.alerts.alert_types.NoAlert;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.alerts.alert_types.alert_factories.BloodPressureAlertFactory;

import java.util.ArrayList;
import java.util.List;
/**
 * Generates alerts related to blood pressure measurements.
 * This class checks for critical thresholds and measurement trends.
 */
public class BloodPressureAlertGenerator implements AlertGeneratorStrategy {

    /**
     * Evaluates blood pressure records and generates alerts if needed.
     *
     * @param patient the patient being evaluated
     * @param records the patient records to evaluate
     * @return an alert if conditions are met, otherwise a no-alert object
     */
    public Alert evaluateData(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> systolicRecords = new ArrayList<>();
        List<PatientRecord> diastolicRecords = new ArrayList<>();
        BloodPressureAlertFactory factory = new BloodPressureAlertFactory();

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
                return factory.createAlert(
                    patient.getPatientId(),
                    "Systolic Critical Threshold",
                    System.currentTimeMillis(),
                    latestSystolic);
            }
        }
        if (!diastolicRecords.isEmpty()) {
            double latestDiastolic =  diastolicRecords.get(diastolicRecords.size() - 1).getMeasurementValue();
            if (latestDiastolic > 120 || latestDiastolic < 60) {
                    return factory.createAlert(
                        patient.getPatientId(),
                        "Diastolic Critical Threshold",
                        System.currentTimeMillis(),
                        latestDiastolic);
            }
        }

        return new NoAlert();
    }

    /**
     * Checks blood pressure records for increasing
     * or decreasing trends.
     *
     * @param patient the patient being evaluated
     * @param records the blood pressure records
     * @param type the type of blood pressure measurement
     * @return an alert if a trend is detected, otherwise a no-alert object
     */
    private Alert checkTrend(Patient patient, List<PatientRecord> records, String type) {
        BloodPressureAlertFactory factory = new BloodPressureAlertFactory();
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
            return factory.createAlert(
                    patient.getPatientId(),
                    condition,
                    System.currentTimeMillis(),
                    thirdMeasure);
        } else if (isDecreasing) {
            String condition = type + " Trend Decreasing";
            return factory.createAlert(
                    patient.getPatientId(),
                    condition,
                    System.currentTimeMillis(),
                    thirdMeasure);
        }


        return new NoAlert();
    }
}
