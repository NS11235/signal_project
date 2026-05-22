package com.alerts.alert_types.alert_factories;

import com.alerts.alert_types.AlertClassic;
import com.alerts.alert_types.ECGAlert;

public class ECGAlertFactory extends AlertFactory {
    @Override
    public AlertClassic createAlert(int patientId, String condition, long timestamp, double value)
    {
        return new ECGAlert(patientId, condition, timestamp);
    }
}
