package com.data_management.file_reading;

import com.data_management.DataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FileParser implements DataReader {

    String path;

    public FileParser(String[] args) throws IOException {
        if(args.length == 0) {
            throw new IOException("File path empty or just spaces");
        }
    }

    // addPatientData(int patientId, double measurementValue, String recordType, long timestamp)
    // "Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n"
    @Override
    public void readData(DataStorage dataStorage) throws FileNotFoundException {
        File file = new File(path);
        if(!file.exists()) {
            throw new FileNotFoundException(path + " does not exist");
        }

        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line.split(", ");
        }
    }
}
