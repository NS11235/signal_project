package com.data_management.file_reading;

import com.data_management.DataStorage;
import com.data_management.arguments_handling.ArgParser;

import java.io.IOException;

public class FileParser implements DataReader {

    String path;
    ArgParser reader;

    public FileParser(String[] args) throws IOException {
        if(args.length == 0) {
            throw new IOException("File path empty or just spaces");
        }
        reader = new ArgParser(args);
        this.path = reader.getPath();

    }

    @Override
    public void readData(DataStorage dataStorage) {

    }
}
