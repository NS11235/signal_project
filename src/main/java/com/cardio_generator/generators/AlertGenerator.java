package com.cardio_generator.generators;

import java.util.Random;

import com.alerts.Alert;
import com.cardio_generator.outputs.OutputStrategy;
import com.data_management.DataStorage;

public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    // AlertStates to alertStates according to 5.2.5
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
    * Monitors patient data and sends an alert when health conditions are triggered.
    * These are: unusual heart-rate, blood pressure and blood sauration
    * This class gets the patient data from a {@link DataStorage} instance and compares
    * it to health criteria. When a condition is triggered a {@link Alert}
    * is created and passed to {@link #triggerAlert}.
    */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alert events for a given patient
     *
     * @param patientId identifier for the patient whom the alert is generated
     * @param outputStrategy strategy used to output alert events
     *
     * @return void
     * 
     * @throws RuntimeException if an error occurs during alert generation or output
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { 
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // Lambda to lambda according to 5.2.5
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
