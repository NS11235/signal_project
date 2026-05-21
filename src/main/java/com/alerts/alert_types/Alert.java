package com.alerts.alert_types;

public interface Alert {
    int getPatientId();
    String getCondition();
    long getTimestamp();
}