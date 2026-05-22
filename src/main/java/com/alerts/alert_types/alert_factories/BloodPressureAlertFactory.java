package com.alerts.alert_types.alert_factories;

import com.alerts.alert_types.AlertClassic;
import com.alerts.alert_types.BloodPressureAlert;

/**
 * Creates blood pressure alert objects.
 */
public class BloodPressureAlertFactory extends AlertFactory {

    /**
     * Creates a blood pressure alert.
     *
     * @param patientId the ID of the patient related to the alert
     * @param condition the condition that triggered the alert
     * @param timestamp the time the alert was created
     * @param value the measurement value associated with the alert
     * @return a blood pressure alert object
     */
    @Override
    public AlertClassic createAlert(int patientId, String condition, long timestamp, double value) {
        return new BloodPressureAlert(patientId, condition, timestamp, value);
    }
}
