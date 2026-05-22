package com.alerts.alert_outputs;

import com.alerts.alert_types.Alert;

/**
 * Outputs alerts to the console.
 * Alert information is displayed in the command line
 * containing the patient ID, condition, and timestamp.
 */
public class ConsoleAlertOutputStrategy implements AlertOutputStrategy {

    /**
     * Outputs a single alert to the console.
     *
     * @param alert the alert to output
     */
    @Override
    public void output(Alert alert) {
        System.out.println("ALERT [" + alert.getPatientId() + "]: "
                + alert.getCondition()
                + " at " + alert.getTimestamp());
    }
}