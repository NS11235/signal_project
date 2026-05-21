package com.alerts.alert_types;

public class ECGAlert extends AlertClassic implements Alert {


    public ECGAlert(int patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}
