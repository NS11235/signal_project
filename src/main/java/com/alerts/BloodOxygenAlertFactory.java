package com.alerts;

public class BloodOxygenAlertFactory extends AlertFactory {
    @Override
    public AlertClassic createAlert(String patientId, String condition, long timestamp) 
    {
        return new AlertClassic(patientId, "Blood oxygen: " + condition, timestamp);
    }
}