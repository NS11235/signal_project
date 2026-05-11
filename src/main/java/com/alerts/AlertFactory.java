package com.alerts;

public abstract class AlertFactory {
    public abstract AlertClassic createAlert(String patientID, String condition, long timestamp);
}
