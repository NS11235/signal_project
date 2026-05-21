package com.alerts.alert_types;

public class NoAlert implements Alert {
    private final String patientId;
    private final String condition;
    private final long timestamp;

    public NoAlert() {
        this.patientId = "0";
        this.condition = "no condition";
        this.timestamp = System.currentTimeMillis();
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
