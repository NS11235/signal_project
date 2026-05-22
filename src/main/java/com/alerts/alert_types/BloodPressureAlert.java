package com.alerts.alert_types;

/**
 * Alert for blood pressure.
 */
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

    /**
     * returns systolic valeu
     */
    public double getSystolic() {
        return systolic;
    }

    /**
     *  diastolic value
     */
    public double getDiastolic() {
        return diastolic;
    }
}
