package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert} method. This method should define the specific 
     * conditions under which an alert will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        List<PatientRecord> records = dataStorage.getRecords(patient.getPatientId(), 0, Long.MAX_VALUE);

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

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
    }
}
