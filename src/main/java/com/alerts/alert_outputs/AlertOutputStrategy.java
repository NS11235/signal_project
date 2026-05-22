package com.alerts.alert_outputs;

import com.alerts.alert_types.Alert;

/**
 * Defines a strategy for outputting alerts.
 */
public interface AlertOutputStrategy {

    /**
     * Outputs a single alert.
     *
     * @param alert the alert to output
     */
    void output(Alert alert);
}
