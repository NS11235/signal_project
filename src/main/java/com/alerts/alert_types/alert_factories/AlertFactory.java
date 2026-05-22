package com.alerts.alert_types.alert_factories;

import com.alerts.alert_types.AlertClassic;

/**
 * Creates alert objects for different alert types.
 */
public abstract class AlertFactory {

    /**
     * Creates an alert with the provided information.
     *
     * @param patientID the ID of the patient related to the alert
     * @param condition the condition that triggered the alert
     * @param timestamp the time the alert was created
     * @param value the measurement value associated with the alert
     * @return a created alert object
     */
    public abstract AlertClassic createAlert(int patientID, String condition, long timestamp, double value);
}