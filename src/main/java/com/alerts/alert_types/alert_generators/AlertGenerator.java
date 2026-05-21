package com.alerts.alert_types.alert_generators;

import com.alerts.alert_types.Alert;
import com.alerts.alert_outputs.AlertOutputStrategy;
import com.alerts.alert_outputs.ConsoleAlertOutputStrategy;
import com.alerts.alert_types.NoAlert;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    
    private final DataStorage dataStorage;
    private final AlertOutputStrategy outputStrategy;
    private final List<AlertGeneratorStrategy> alertStrategies;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.outputStrategy = new ConsoleAlertOutputStrategy();
        this.alertStrategies = getStrategies();
    }


    private List<AlertGeneratorStrategy> getStrategies() {
        List<AlertGeneratorStrategy> strategies = new ArrayList<>();

        strategies.add(new BloodPressureAlertGenerator());
        strategies.add(new BloodSaturationAlertGenerator());
        strategies.add(new HypotensiveHypoxemiaAlertGenerator());
        strategies.add(new ECGAlertGenerator());

        return strategies;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, the respective Alert Generator will trigger
     * an alert output.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        List<PatientRecord> records = dataStorage.getRecords(patient.getPatientId(), 0, Long.MAX_VALUE);

        for (AlertGeneratorStrategy strategy : alertStrategies) {
            Alert alert = strategy.evaluateData(patient, records);
            if (!(alert instanceof NoAlert)) {
                outputStrategy.output(alert);
            }
        }
    }
}
