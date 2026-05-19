package com.alerts.alert_outputs;

import com.alerts.Alert;

public interface AlertOutputStrategy {
    void output(Alert alert);
}
