package com.alerts.alert_types;

public class BloodPressureAlert extends AlertClassic implements Alert  {

    private double systolic;
    private double diastolic;

    public BloodPressureAlert(int patientId, String condition, long timestamp, double value) {
        super(patientId, condition, timestamp);
        if (condition.equals("Diastolic")) {
            systolic = value;
        } else if (condition.equals("Systolic")) {
            diastolic = value;
        }
    }

    public double getSystolic() {
        return systolic;
    }

    public double getDiastolic() {
        return diastolic;
    }
}
