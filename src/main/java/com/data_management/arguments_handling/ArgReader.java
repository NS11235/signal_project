package com.data_management.arguments_handling;

import java.io.IOException;
import java.util.Map;

public interface ArgReader {
    Map<String, String> readArguments() throws IOException;
}
