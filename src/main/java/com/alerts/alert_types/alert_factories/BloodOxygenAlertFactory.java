package com.alerts.alert_types.alert_factories;

import com.alerts.alert_types.AlertClassic;
import com.alerts.alert_types.BloodSaturationAlert;

public class BloodOxygenAlertFactory extends AlertFactory {
    @Override
    public AlertClassic createAlert(int patientId, String condition, long timestamp, double value)
    {
        return new BloodSaturationAlert(patientId, "Blood oxygen: " + condition, timestamp, value);
    }
}
