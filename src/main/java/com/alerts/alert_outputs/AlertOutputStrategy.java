package com.alerts.alert_outputs;

import com.alerts.alert_types.Alert;

public interface AlertOutputStrategy {
    void output(Alert alert);
}
