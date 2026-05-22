package com.alerts.alert_types;

import com.alerts.AlertDecorator;

public class PriorityAlertDecorator extends AlertDecorator {
    public PriorityAlertDecorator(Alert alert) {
        super(alert);
    }

    @Override
    public String getCondition() {
        return "PRIORITY! " + decoratedAlert.getCondition();
    }
}