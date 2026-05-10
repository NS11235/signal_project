package com.data_management;

import java.io.IOException;

public class FileParser implements DataReader {

    String path;
    ArgParser reader;

    public FileParser(String[] args) throws IOException {
        if(path.trim().isEmpty()) {
            throw new IOException("File path empty or just spaces");
        }
        reader = new ArgParser(path);
        this.path = reader.getPath();

    }

    @Override
    public void readData(DataStorage dataStorage) {

    }
}
