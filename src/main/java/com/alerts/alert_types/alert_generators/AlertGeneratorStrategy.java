package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public interface AlertGeneratorStrategy {
    Alert evaluateData(Patient patient, List<PatientRecord> patientRecords);
}
