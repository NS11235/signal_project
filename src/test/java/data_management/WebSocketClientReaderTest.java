package data_management;

import com.data_management.DataStorage;
import com.data_management.WebSocketClientReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;


class WebSocketClientReaderTest {

    private WebSocketClientReader reader;

    @BeforeEach
    void setUp() throws Exception {
        reader = new WebSocketClientReader(new URI("ws://localhost:8080"));
        reader.readData_forTestOnly(DataStorage.getInstance());
    }

    @Test
    void testFewerFields() {
        assertDoesNotThrow(() -> reader.onMessage("1,1716400000000,HeartRate"));
    }

    @Test
    void testMoreFields() {
        assertDoesNotThrow(() -> reader.onMessage("1,1716400000000,HeartRate,aaaaaaaaaaaaaaaa,16121989,25121989"));
    }

    @Test
    void testIDnotInt() {
        assertDoesNotThrow(() -> reader.onMessage("aaaaaaaa,1716400000000,HeartRate,72.5"));
    }

    @Test
    void conditionNotString() {
        assertDoesNotThrow(() -> reader.onMessage("1,1716400000000,1989,1989"));
    }

    @Test
    void testEmptyMessage() {
        assertDoesNotThrow(() -> reader.onMessage(""));
    }
}