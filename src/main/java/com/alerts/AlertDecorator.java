package com.alerts;

import com.alerts.alert_types.Alert;
/** Abstract base decorator for alerts
*/
public abstract class AlertDecorator implements Alert {
    public  Alert decoratedAlert;

      /**
     * Wraps the given alert.
     *
     * @param alert the alert to decorate
     */
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
