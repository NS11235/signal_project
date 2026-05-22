package com.alerts.alert_types;

/**
 * Base for alerts.
 */
public class AlertClassic implements Alert {
    private int patientId;
    private String condition;
    private long timestamp;

    /**
     * Creates a new alert.
     */
    public AlertClassic(int patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }
    
    /**
     * Returns the patient ID.
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Returns the alert condition.
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Returns the timestamp.
     */
    public long getTimestamp() {
        return timestamp;
    }
}
