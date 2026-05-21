package com.alerts.alert_types;

public class HypotensiveHypoxemiaAlert implements Alert {
    private final String patientId;
    private final String condition;
    private final long timestamp;

    public HypotensiveHypoxemiaAlert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}
