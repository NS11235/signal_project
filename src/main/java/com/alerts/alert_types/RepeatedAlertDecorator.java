package com.alerts.alert_types;

import com.alerts.AlertDecorator;
/**
 * Decorator for repeated alerts.
 */
public class RepeatedAlertDecorator extends AlertDecorator {
    private int ct;

    public RepeatedAlertDecorator(Alert alert, int repeatCount) {
        super(alert);
        this.ct=ct;
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition() + "alert repeated " + ct + "times";
    }
}
