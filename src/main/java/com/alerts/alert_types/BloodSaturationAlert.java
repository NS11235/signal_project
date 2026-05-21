package com.alerts.alert_types;

public class BloodSaturationAlert extends AlertClassic implements Alert {

    private double saturationValue;

    public BloodSaturationAlert(int patientId, String condition, long timestamp, double value) {
        super(patientId, condition, timestamp);
        this.saturationValue = value;
    }

    public double getSaturationValue() {
        return saturationValue;
    }

}
