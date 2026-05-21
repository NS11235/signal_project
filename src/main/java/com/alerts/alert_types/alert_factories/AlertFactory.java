package com.alerts.alert_types.alert_factories;

import com.alerts.alert_types.AlertClassic;

public abstract class AlertFactory {
    public abstract AlertClassic createAlert(int patientID, String condition, long timestamp);
}
