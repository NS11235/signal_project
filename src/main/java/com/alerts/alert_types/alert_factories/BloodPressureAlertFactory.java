package com.alerts.alert_types.alert_factories;

import com.alerts.alert_types.AlertClassic;

public class BloodPressureAlertFactory extends AlertFactory {
    @Override
    public AlertClassic createAlert(String patientId, String condition, long timestamp)
    {
        return new AlertClassic(patientId, "Blood-pressure: " + condition, timestamp);
    }
}
