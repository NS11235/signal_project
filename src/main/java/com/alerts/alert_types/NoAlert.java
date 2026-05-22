package com.alerts.alert_types;
/**
 * Represents the lack of an alert.
 */
public class NoAlert extends AlertClassic implements Alert {

    public NoAlert() {
        super(0, "no condition", System.currentTimeMillis());
    }

}
