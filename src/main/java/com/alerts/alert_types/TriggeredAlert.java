package com.alerts.alert_types;
/**
 * Alert triggered by a cause.
 */
public class TriggeredAlert extends AlertClassic implements Alert{

    public TriggeredAlert(int patientId, long timestamp) {
        super(patientId, "Triggered", timestamp);
    }

}
