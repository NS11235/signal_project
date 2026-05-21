package com.alerts.alert_types;

public class TriggeredAlert implements Alert{
    private String patientId;
    private String condition;
    private long timestamp;

    public TriggeredAlert(String patientId, long timestamp) {
        this.patientId = patientId;
        this.condition = "Triggered";
        this.timestamp = timestamp;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
