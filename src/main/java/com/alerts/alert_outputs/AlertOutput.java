package com.alerts.alert_outputs;

import com.alerts.alert_types.Alert;

import java.util.List;

/**
 * Handles the output of alerts using a specified output strategy.
 * This class gives the output process to the provided strategy,
 * allowing different output methods to be used.
 */
public class AlertOutput {
    private final AlertOutputStrategy outputStrategy;

    /**
     * Creates an AlertOutput object with the given output strategy.
     *
     * @param outputStrategy the strategy used to output alerts
     */
    public AlertOutput(AlertOutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    /**
     * Outputs a list of alerts using the selected output strategy.
     *
     * @param alerts the list of alerts to output
     */
    public void outputAlerts(List<Alert> alerts) {
        for (Alert alert : alerts) {
            outputStrategy.output(alert);
        }
    }
}