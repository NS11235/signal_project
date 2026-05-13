package com.alerts.BloodPressureAlert;

import com.alerts.Alert;

public class BloodPressureAlert implements Alert {

    private int patientId;
    private String condition;
    private long timestamp;
    private double systolic;
    private double diastolic;

    public BloodPressureAlert(int patientId, String condition, long timestamp, double value) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
        if (condition.equals("Diastolic")) {
            systolic = value;
        } else if (condition.equals("Systolic")) {
            diastolic = value;
        }
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

    public double getSystolic() {
        return systolic;
    }

    public double getDiastolic() {
        return diastolic;
    }
}
