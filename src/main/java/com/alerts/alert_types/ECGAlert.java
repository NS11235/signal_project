package com.alerts.alert_types;
/**
 * Alert related to ECG readings.
 */
public class ECGAlert extends AlertClassic implements Alert {


    public ECGAlert(int patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}
