package com.data_management;

import com.data_management.file_reading.DataReader;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The class WebSocketClientReader connects to a server and gets
 * the patient data from there, storing it in DataStorage
 */
public class WebSocketClientReader extends WebSocketClient implements DataReader {

    private DataStorage dataStorage;

    /**
     * @param serverUri
     */
    public WebSocketClientReader(URI serverUri) {
        super(serverUri);
    }

    /**
     * makes the connection when we start websocket
     */
    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to WebSocket server: " + getURI());
    }

    /**
     * dalled everytime there is new input from the server
     */
    @Override
    public void onMessage(String message) {
        if (dataStorage == null) {
            System.err.println("WebSocketClientReader: dataStorage is null, cannot store message.");
            return;
        }
        try {
            String[] parts = message.split(",");
            if (parts.length != 4) {
                System.err.println("Malformed message (expected 4 fields): " + message);
                return;
            }
            int patientId = Integer.parseInt(parts[0].trim());
            long timestamp = Long.parseLong(parts[1].trim());
            String label   = parts[2].trim();
            double value   = Double.parseDouble(parts[3].trim());

            dataStorage.addPatientData(patientId, value, label, timestamp);

        } catch (NumberFormatException e) {
            System.err.println("Could not parse message: " + message + " | Error: " + e.getMessage());
        }
    }

    /**
     * called when the connection closes
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket connection closed. Code: " + code + ", Reason: " + reason);
    }

    /**
     * Called when an error occurs
     */
    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: " + ex.getMessage());
        ex.printStackTrace();
    }

    /**
     * Implements DataReader.readData()
     * @param dataStorage storage of parsed text
     * @throws IOException if the connection cannot be made
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        this.dataStorage = dataStorage;
        try {
            boolean connected = connectBlocking();
            if (!connected) {
                throw new IOException("Failed to connect to WebSocket server at " + getURI());
            }
            System.out.println("WebSocketClientReader is now live and receiving data...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Connection interrupted: " + e.getMessage(), e);
        }
    }

    public static WebSocketClientReader create(String url) throws IOException {
        try {
            return new WebSocketClientReader(new URI(url));
        } catch (URISyntaxException e) {
            throw new IOException("Invalid WebSocket URL: " + url, e);
        }
    }
}