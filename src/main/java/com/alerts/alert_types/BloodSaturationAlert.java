package com.alerts.alert_types;

public class BloodSaturationAlert implements Alert {

    private int patientId;
    private String condition;
    private long timestamp;
    private double saturationValue;

    public BloodSaturationAlert(int patientId, String condition, long timestamp, double value) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
        this.saturationValue = value;
    }

    @Override
    public String getPatientId() {
        return Integer.toString(patientId);
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public double getSaturationValue() {
        return saturationValue;
    }

}
