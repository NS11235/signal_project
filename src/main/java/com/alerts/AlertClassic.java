package com.alerts;

// Represents an alert
public class AlertClassic implements Alert{
    private String patientId;
    private String condition;
    private long timestamp;

    public AlertClassic(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
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
