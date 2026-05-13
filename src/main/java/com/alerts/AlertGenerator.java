package com.alerts;

import com.alerts.BloodPressureAlert.BloodPressureAlert;
import com.alerts.BloodPressureAlert.BloodPressureAlertGenerator;
import com.alerts.BloodSaturationAlert.BloodSaturationAlertGenerator;
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

        boolean hasBloodPressureRecords = false;
        boolean hasBloodSaturationRecords = false;

        for  (PatientRecord record : records) {
            if (record.getRecordType().contains("SystolicPressure") || record.getRecordType().contains("DiastolicPressure")) {
                hasBloodPressureRecords = true;
            } else if (record.getRecordType().contains("BloodSaturation")) {
                hasBloodSaturationRecords = true;
            }
        }
        if (hasBloodPressureRecords) {
            BloodPressureAlertGenerator bloodPressureAlertGenerator = new BloodPressureAlertGenerator();
            bloodPressureAlertGenerator.evaluateData(patient, records);
        }
        if (hasBloodSaturationRecords) {
            BloodSaturationAlertGenerator bloodSaturationAlertGenerator = new BloodSaturationAlertGenerator();
            bloodSaturationAlertGenerator.evaluateData(patient, records);
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
