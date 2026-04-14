package com.cardio_generator.outputs;
/**
 * This interface ensures is used by Generator classes
 * so that the generator strategy classes all have an generate() method
 */
public interface OutputStrategy {
    void output(int patientId, long timestamp, String label, String data);
}
