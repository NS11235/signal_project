package com.alerts.alert_types;

public interface Alert {
    String getPatientId();
    String getCondition();
    long getTimestamp();
}