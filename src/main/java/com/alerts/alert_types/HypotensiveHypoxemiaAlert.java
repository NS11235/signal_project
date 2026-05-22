package com.alerts.alert_types;
/**
 * Alert for hypotensivity and hypoxemia.
 */
public class HypotensiveHypoxemiaAlert extends AlertClassic implements Alert {

    public HypotensiveHypoxemiaAlert(int patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

}
