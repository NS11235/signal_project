package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * This interface ensures is used by Output classes 
 * so that the output strategy classes all have an output() method
 */
public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
