package com.alerts.alert_outputs;

import com.alerts.alert_types.Alert;

import java.util.List;

public class AlertOutput {
    private final AlertOutputStrategy outputStrategy;

    public AlertOutput(AlertOutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    public void outputAlerts(List<Alert> alerts) {
        for (Alert alert : alerts) {
            outputStrategy.output(alert);
        }
    }
}
