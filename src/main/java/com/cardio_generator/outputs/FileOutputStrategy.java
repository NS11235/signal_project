package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements {@link OutputStrategy} by writing patient data to text files.
 * Each label gets its own file under the specified base directory.
 */
public class FileOutputStrategy implements OutputStrategy {

    /** The base directory where output files are put. */
    // Changed BaseDirectory to baseDirectory according to section 5.2.5
    private String baseDirectory;

    /** Maps each label to its corresponding file path. */
    // Changed file_map to fileMap according to section 5.2.4 non-static
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Writes formatted patient data to a file associated with the given label.
     *
     * @param patientId identifier for the patient
     * @param timestamp timestamp for data entry
     * @param label used to determine the output file
     * @param data data to be written to the file
     *
     * @return void
     * 
     * @throws RuntimeException if an unexpected error occurs during file writing
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
          Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }

        // Changed FilePath to filePath according to section 5.2.5
        String filePath = fileMap.computeIfAbsent(
            label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}