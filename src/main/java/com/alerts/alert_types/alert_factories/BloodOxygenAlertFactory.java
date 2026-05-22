package com.alerts.alert_types.alert_factories;

import com.alerts.alert_types.AlertClassic;
import com.alerts.alert_types.BloodSaturationAlert;

/**
 * Creates blood oxygen alert objects.
 */
public class BloodOxygenAlertFactory extends AlertFactory {

    /**
     * Creates a blood oxygen alert.
     *
     * @param patientId the ID of the patient related to the alert
     * @param condition the condition that triggered the alert
     * @param timestamp the time the alert was created
     * @param value the measurement value associated with the alert
     * @return a blood oxygen alert object
     */
    @Override
    public AlertClassic createAlert(int patientId, String condition, long timestamp, double value) {
        return new BloodSaturationAlert(patientId, condition, timestamp, value);
    }
}