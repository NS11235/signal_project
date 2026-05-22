package com.data_management.file_reading;

import com.data_management.DataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
/** Reads patient data and stores it in DataStorage. */
public class FileParser implements DataReader {

    String path;

    /**
     * Constructs a FileParser for the directory given in args[0].
     *
     * @param args command-line arguments; args[0] must be a valid directory path
     * @throws IOException if args is empty or the path is not a valid directory
     */
    public FileParser(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IOException("Directory path cannot be empty");
        }
        path = args[0];
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException(path + " is not a valid directory");
        }
    }

    /**
     * Reads all .txt files in the directory and stores parsed records in dataStorage.
     *
     * @param dataStorage the storage where parsed records will be saved
     * @throws FileNotFoundException if no .txt files are found in the directory
     */
    @Override
    public void readData(DataStorage dataStorage) throws FileNotFoundException {
        File dir = new File(path);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            throw new FileNotFoundException("No .txt files found in directory: " + path);
        }

        for (File file : files) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(", ");

                int patientId = Integer.parseInt(parts[0].substring("Patient ID: ".length()));

                long timestamp = Long.parseLong(parts[1].substring("Timestamp: ".length()));

                String label = parts[2].substring("Label: ".length());

                double data = Double.parseDouble(parts[3].substring("Data: ".length()));

                dataStorage.addPatientData(patientId, data, label, timestamp);
            }
            scanner.close();
        }
    }
}
