package com.data_management.file_reading;

import com.data_management.DataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileParser implements DataReader {

    String path;

    public FileParser(String[] args) throws IOException {
        if(args.length == 0) {
            throw new IOException("File path empty or just spaces");
        }
        path = args[0];
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
        ArrayList<String[]> data = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] dataLine = new String[4];
            dataLine[0] = line.split(", ")[0].substring("Patient ID: ".length());
            dataLine[1] = line.split(", ")[1].substring("Timestamp: ".length());
            dataLine[2] = line.split(", ")[2].substring("Label: ".length());
            dataLine[3] = line.split(", ")[3].substring("Data: ".length());
            data.add(dataLine);
        }
        for (String[] dataLine : data) {
            dataStorage.addPatientData(
                    Integer.parseInt(dataLine[0]),
                    Double.parseDouble(dataLine[3]),
                    dataLine[2],
                    Long.parseLong(dataLine[1]));
        }
    }
}
