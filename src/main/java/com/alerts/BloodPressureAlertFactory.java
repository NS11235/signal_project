package com.alerts;

public class BloodPressureAlertFactory extends AlertFactory{
    @Override
    public AlertClassic createAlert(String patientId, String condition, long timestamp) 
    {
        return new AlertClassic(patientId, "Blood-pressure: " + condition, timestamp);
    }
}
