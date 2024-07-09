import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvParser {
    private static final Logger Logger = LoggerFactory.getLogger(CsvParser.class);

    public List<Map<String, String>> parseCsv(File file) {
        List<Map<String, String>> recordsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return recordsList;
            }

            String[] headers = headerLine.split(",");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> record = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    record.put(headers[i], values[i]);
                }
                recordsList.add(record);
            }
        } catch (IOException e) {
            Logger.error("Error reading CSV file: " + e.getMessage(), e);
        }
        return recordsList;
    }
}
