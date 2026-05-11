package com.alerts;

public class ECGAlertFactory extends AlertFactory {
    @Override
    public AlertClassic createAlert(String patientId, String condition, long timestamp) 
    {
        return new AlertClassic(patientId, "ECG" + condition, timestamp);
    }
}