package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Defines a strategy for evaluating patient data
 * and generating alerts.
 */
public interface AlertGeneratorStrategy {

    /**
     * Evaluates patient records and determines whether
     * an alert should be generated.
     *
     * @param patient the patient being evaluated
     * @param patientRecords the patient's records
     * @return an alert if a condition is met, otherwise a no-alert object
     */
    Alert evaluateData(Patient patient, List<PatientRecord> patientRecords);
}