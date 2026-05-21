package com.alerts.alert_types;

public class NoAlert extends AlertClassic implements Alert {

    public NoAlert() {
        super(0, "no condition", System.currentTimeMillis());
    }

}
