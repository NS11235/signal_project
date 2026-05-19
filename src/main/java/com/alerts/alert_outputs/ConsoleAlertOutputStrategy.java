package com.alerts.alert_outputs;

import com.alerts.Alert;

public class ConsoleAlertOutputStrategy implements AlertOutputStrategy{
    @Override
    public void output(Alert alert) {
        System.out.println("ALERT [" + alert.getPatientId() + "]: "
                + alert.getCondition()
                + " at " + alert.getTimestamp());
    }
}
