package com.data_management.arguments_handling;

import java.io.IOException;
import java.util.ArrayList;

public interface ArgReader {
    public ArrayList<String> readArguments() throws IOException;
}
