package data_management;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.file_reading.FileParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileParserTest {

    Path directory = Files.createTempDirectory("FileParserTest");

    public FileParserTest() throws IOException {
    }

    void resetSingleton() throws Exception {
        java.lang.reflect.Field field = DataStorage.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
    }

    private File writeTxtFile(String filename, String... lines) throws IOException {
        File file = directory.resolve(filename).toFile();
        try (FileWriter writer = new FileWriter(file)) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        }
        return file;
    }

    @Test
    void testConstructorThrowsWhenNoArgsProvided() throws Exception {
        resetSingleton();
        boolean threw = false;
        try {
            new FileParser(new String[]{});
        } catch (IOException e) {
            threw = true;
        }
        assert threw;
    }

    @Test
    void testConstructorThrowsForNonExistentDirectory() throws Exception {
        resetSingleton();
        boolean threw = false;
        try {
            new FileParser(new String[]{"/this/path/does/not/exist"});
        } catch (IOException e) {
            threw = true;
        }
        assert threw;
    }

    @Test
    void testConstructorThrowsWhenPathIsAFile() throws Exception {
        resetSingleton();
        File file = directory.resolve("notADir.txt").toFile();
        file.createNewFile();

        boolean threw = false;
        try {
            new FileParser(new String[]{file.getAbsolutePath()});
        } catch (IOException e) {
            threw = true;
        }
        assert threw;
    }

    @Test
    void testConstructorSucceedsWithValidDirectory() throws Exception {
        resetSingleton();
        boolean threw = false;
        try {
            new FileParser(new String[]{directory.toString()});
        } catch (IOException e) {
            threw = true;
        }
        assert !threw;
    }

    @Test
    void testReadDataParsesOneRecordCorrectly() throws Exception {
        resetSingleton();
        writeTxtFile("data.txt",
                "Patient ID: 1, Timestamp: 1700000000000, Label: HeartRate, Data: 72.0");

        DataStorage storage = DataStorage.getInstance();
        FileParser parser = new FileParser(new String[]{directory.toString()});
        parser.readData(storage);

        List<PatientRecord> records = storage.getRecords(1, 0, Long.MAX_VALUE);

        assert records.size() == 1;
        assert records.get(0).getPatientId() == 1;
        assert records.get(0).getRecordType().equals("HeartRate");
        assert records.get(0).getMeasurementValue() == 72.0;
        assert records.get(0).getTimestamp() == 1700000000000L;
    }

    @Test
    void testReadDataParsesMultipleLinesInOneFile() throws Exception {
        resetSingleton();
        writeTxtFile("data.txt",
                "Patient ID: 1, Timestamp: 1700000000000, Label: HeartRate, Data: 70.0",
                "Patient ID: 1, Timestamp: 1700000001000, Label: HeartRate, Data: 75.0",
                "Patient ID: 1, Timestamp: 1700000002000, Label: HeartRate, Data: 80.0");

        DataStorage storage = DataStorage.getInstance();
        FileParser parser = new FileParser(new String[]{directory.toString()});
        parser.readData(storage);

        List<PatientRecord> records = storage.getRecords(1, 0, Long.MAX_VALUE);

        assert records.size() == 3;
    }

    @Test
    void testReadDataParsesRecordsFromMultipleFiles() throws Exception {
        resetSingleton();
        writeTxtFile("fileA.txt",
                "Patient ID: 1, Timestamp: 1700000000000, Label: HeartRate, Data: 70.0");
        writeTxtFile("fileB.txt",
                "Patient ID: 2, Timestamp: 1700000000000, Label: BloodSaturation, Data: 96.0");

        DataStorage storage = DataStorage.getInstance();
        FileParser parser = new FileParser(new String[]{directory.toString()});
        parser.readData(storage);

        List<PatientRecord> patient1Records = storage.getRecords(1, 0, Long.MAX_VALUE);
        List<PatientRecord> patient2Records = storage.getRecords(2, 0, Long.MAX_VALUE);

        assert patient1Records.size() == 1;
        assert patient2Records.size() == 1;
    }

    @Test
    void testReadDataAssignsRecordsToDifferentPatientsCorrectly() throws Exception {
        resetSingleton();
        writeTxtFile("data.txt",
                "Patient ID: 10, Timestamp: 1700000000000, Label: HeartRate, Data: 60.0",
                "Patient ID: 20, Timestamp: 1700000001000, Label: HeartRate, Data: 90.0");

        DataStorage storage = DataStorage.getInstance();
        FileParser parser = new FileParser(new String[]{directory.toString()});
        parser.readData(storage);

        List<PatientRecord> p10 = storage.getRecords(10, 0, Long.MAX_VALUE);
        List<PatientRecord> p20 = storage.getRecords(20, 0, Long.MAX_VALUE);

        assert p10.size() == 1;
        assert p10.get(0).getMeasurementValue() == 60.0;
        assert p20.size() == 1;
        assert p20.get(0).getMeasurementValue() == 90.0;
    }

    @Test
    void testReadDataIgnoresNonTxtFiles() throws Exception {
        resetSingleton();
        writeTxtFile("data.txt",
                "Patient ID: 1, Timestamp: 1700000000000, Label: HeartRate, Data: 72.0");

        File csv = directory.resolve("ignored.csv").toFile();
        try (FileWriter w = new FileWriter(csv)) {
            w.write("Patient ID: 2, Timestamp: 1700000000000, Label: HeartRate, Data: 99.0\n");
        }

        DataStorage storage = DataStorage.getInstance();
        FileParser parser = new FileParser(new String[]{directory.toString()});
        parser.readData(storage);

        List<PatientRecord> patient2Records = storage.getRecords(2, 0, Long.MAX_VALUE);

        assert patient2Records.isEmpty();
    }

    @Test
    void testReadDataParsesDoubleValueCorrectly() throws Exception {
        resetSingleton();
        writeTxtFile("data.txt",
                "Patient ID: 1, Timestamp: 1700000000000, Label: BloodPressure, Data: 119.5");

        DataStorage storage = DataStorage.getInstance();
        FileParser parser = new FileParser(new String[]{directory.toString()});
        parser.readData(storage);

        List<PatientRecord> records = storage.getRecords(1, 0, Long.MAX_VALUE);

        assert records.get(0).getMeasurementValue() == 119.5;
    }

    @Test
    void testReadDataThrowsWhenDirectoryContainsNoTxtFiles() throws Exception {
        resetSingleton();

        DataStorage storage = DataStorage.getInstance();
        FileParser parser = new FileParser(new String[]{directory.toString()});

        boolean threw = false;
        try {
            parser.readData(storage);
        } catch (java.io.FileNotFoundException e) {
            threw = true;
        }
        assert threw;
    }
}
