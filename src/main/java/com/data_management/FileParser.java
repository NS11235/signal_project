package com.data_management;

import java.io.IOException;

public class FileParser implements DataReader {

    public FileParser(String path) throws IOException {
        if(path.trim().isEmpty()) {
            throw new IOException("File path empty or just spaces");
        }
    }

    @Override
    public void readData(DataStorage dataStorage) {

    }
}
