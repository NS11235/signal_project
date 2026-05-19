package com.alerts;

import com.alerts.BloodPressureAlert.BloodPressureAlertGenerator;
import com.alerts.BloodSaturationAlert.BloodSaturationAlertGenerator;
import com.alerts.HypotensiveHypoxemiaAlert.HypotensiveHypoxemiaAlertGenerator;
import com.alerts.alert_outputs.AlertOutputStrategy;
import com.alerts.alert_outputs.ConsoleAlertOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    
    private final DataStorage dataStorage;
    private final AlertOutputStrategy outputStrategy;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.outputStrategy = new ConsoleAlertOutputStrategy();
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, the respective Alert Generator will trigger
     * an alert output.
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
        if (hasBloodPressureRecords && hasBloodSaturationRecords) {
            HypotensiveHypoxemiaAlertGenerator hypotensiveHypoxemiaAlertGenerator = new HypotensiveHypoxemiaAlertGenerator(outputStrategy);
            hypotensiveHypoxemiaAlertGenerator.evaluateData(patient, records);
        }
    }
}
