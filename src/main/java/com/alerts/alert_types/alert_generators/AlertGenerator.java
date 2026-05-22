package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_types.NoAlert;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates alerts by evaluating patient data against
 * a set of alert generation strategies.
 */
public class AlertGenerator {

    private final DataStorage dataStorage;
    private final List<AlertGeneratorStrategy> alertStrategies;

    /**
     * Creates an AlertGenerator with a data storage source.
     *
     * @param dataStorage the data storage system containing patient records
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.alertStrategies = getStrategies();
    }

    /**
     * Creates a list of alert generation strategies.
     *
     * @return a list of alert generation strategies
     */
    private List<AlertGeneratorStrategy> getStrategies() {
        List<AlertGeneratorStrategy> strategies = new ArrayList<>();

        strategies.add(new BloodPressureAlertGenerator());
        strategies.add(new BloodSaturationAlertGenerator());
        strategies.add(new HypotensiveHypoxemiaAlertGenerator());
        strategies.add(new ECGAlertGenerator());

        return strategies;
    }

    /**
     * Generates alerts for a patient by evaluating patient records.
     *
     * @param patient the patient whose records are evaluated
     * @return a list of generated alerts
     */
    public List<Alert> generateAlerts(Patient patient) {
        List<PatientRecord> records = dataStorage.getRecords(
                patient.getPatientId(), 0, Long.MAX_VALUE);

        List<Alert> alerts = new ArrayList<>();

        for (AlertGeneratorStrategy strategy : alertStrategies) {
            Alert alert = strategy.evaluateData(patient, records);
            if (!(alert instanceof NoAlert)) {
                alerts.add(alert);
            }
        }

        return alerts;
    }
}