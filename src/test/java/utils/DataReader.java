package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.util.*;

public class DataReader {

    // Reads a CSV into a List of maps (column header → cell value)
    public static List<Map<String, String>> readCsv(String path) {
        List<Map<String, String>> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] headers = reader.readNext(); // first row
            String[] row;
            while ((row = reader.readNext()) != null) {
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    map.put(headers[i].trim(), row[i].trim());
                }
                records.add(map);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV: " + path, e);
        }
        return records;
    }

    // (Optional) read Excel if you prefer .xlsx
    // public static List<Map<String,String>> readExcel(...) { ... }
}
