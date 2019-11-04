package com.codechallange.service;

import com.codechallange.model.Transaction;
import com.codechallange.util.Constants;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.joda.time.DateTime;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecordsReader {
    private String filePath;
    private String nameToCheck;
    private DateTime to;
    private DateTime from;

    public RecordsReader(String filePath, String merchantNameToCheck, DateTime to, DateTime from) {
        this.filePath = filePath;
        this.nameToCheck = merchantNameToCheck;
        this.to = to;
        this.from = from;
    }

    Map<String, Transaction> readValidData() throws IOException {
        FileReader filereader = new FileReader(filePath);
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withSkipLines(1)
                .build();
        try {
            Map<String, Transaction> map = new HashMap<String, Transaction>();
            for (String[] row : csvReader) {
                populateMap(map, row);
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            filereader.close();
            csvReader.close();
        }
    }

    private void populateMap(Map<String, Transaction> map, String[] row) {
        Transaction.Type type = Transaction.Type.valueOf(row[4].trim());
        String id = row[0].trim();
        DateTime time = Constants.joda.parseDateTime(row[1].trim());
        double amount = Double.parseDouble(row[2].trim());
        String merchant = row[3].trim();

        if (type == Transaction.Type.REVERSAL) {
            map.remove(row[5].trim());
        } else {
            if (merchant.equalsIgnoreCase(nameToCheck) && time.compareTo(from) >= 0 && time.compareTo(to) < 0) {
                map.put(id, new Transaction(id, time, amount, merchant, type, null));
            }
        }
    }
}