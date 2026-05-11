package com.alerts;

public abstract class AlertDecorator implements Alert{
    public  Alert decoratedAlert;

    public AlertDecorator(Alert alert) {
        this.decoratedAlert= alert;
    }

    @Override
    public String getPatientId() 
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
