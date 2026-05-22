package com.alerts.alert_types.alert_factories;

import com.alerts.alert_types.AlertClassic;
import com.alerts.alert_types.ECGAlert;

/**
 * Creates ECG alert objects.
 */
public class ECGAlertFactory extends AlertFactory {

    /**
     * Creates an ECG alert.
     *
     * @param patientId the ID of the patient related to the alert
     * @param condition the condition that triggered the alert
     * @param timestamp the time the alert was created
     * @param value the measurement value associated with the alert
     * @return an ECG alert object
     */
    @Override
    public AlertClassic createAlert(int patientId, String condition, long timestamp, double value) {
        return new ECGAlert(patientId, condition, timestamp);
    }
}
