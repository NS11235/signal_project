package com.alerts.alert_types;
/**
 * Interface for all alert types.
 */
public interface Alert {
     /**
     * Returns the patient ID.
     */
    int getPatientId();
     /**
     * Returns the alert condition.
     */
    String getCondition();
     /**
     * Returns the alert timestamp.
     */
    long getTimestamp();
}
