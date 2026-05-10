package com.data_management;

import com.sun.jdi.connect.Connector;

import java.io.IOException;
import java.util.ArrayList;

public interface ArgReader {
    public ArrayList<String> readArguments() throws IOException;
}
