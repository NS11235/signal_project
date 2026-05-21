package com.alerts.alert_types;

public class TriggeredAlert extends AlertClassic implements Alert{

    public TriggeredAlert(int patientId, long timestamp) {
        super(patientId, "Triggered", timestamp);
    }

}
