package com.alerts;

import com.alerts.alert_types.Alert;

public abstract class AlertDecorator implements Alert {
    public  Alert decoratedAlert;

    public AlertDecorator(Alert alert) {
        this.decoratedAlert= alert;
    }

    @Override
    public int getPatientId()
    {
        return decoratedAlert.getPatientId(); 
    }

    @Override
    public String getCondition() 
    { 
        return decoratedAlert.getCondition(); 
    }

    @Override
    public long getTimestamp() 
    { 
        return decoratedAlert.getTimestamp(); 
    }
}
