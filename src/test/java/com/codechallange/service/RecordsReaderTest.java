package com.codechallange.service;

import com.google.common.collect.Iterables;
import com.codechallange.model.Transaction;
import com.codechallange.util.Constants;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RecordsReaderTest {

    private RecordsReader recordsReader;
    private ClassLoader classLoader;
    private String resourceRoot;

    @Before
    public void setUp() {
        classLoader = getClass().getClassLoader();
        resourceRoot = "com/codechallange/service/";

    }

    @Test
    public void readValidData() throws IOException {
        String filePath = classLoader.getResource(resourceRoot + "problemStatementData.csv").getFile();
        recordsReader = new RecordsReader(filePath , "Kwik-E-Mart", Constants.joda.parseDateTime("20/08/2018 13:00:00"),
                Constants.joda.parseDateTime("20/08/2018 12:00:00"));

        Map<String, Transaction> actualMap = recordsReader.readValidData();

        assertEquals(actualMap.entrySet().size(), 1);
        assertEquals(Iterables.getOnlyElement(actualMap.values()),
                Transaction.normalTransaction("WLMFRDGD",Constants.joda.parseDateTime("20/08/2018 12:45:33"), 59.99, "Kwik-E-Mart"));
    }

    @Test
    public void readValidDataFromEmptyFile() throws IOException {
        String filePath = classLoader.getResource(resourceRoot + "empty.csv").getFile();
        recordsReader = new RecordsReader(filePath , "Kwik-E-Mart", Constants.joda.parseDateTime("20/08/2018 13:00:00"),
                Constants.joda.parseDateTime("20/08/2018 12:00:00"));

        Map<String, Transaction> actualMap = recordsReader.readValidData();

        assertEquals(actualMap.entrySet().size(), 0);
    }

    @Test
    public void readValidDataFromAllReverseTransactions() throws IOException {
        String filePath = classLoader.getResource(resourceRoot + "reverse.csv").getFile();
        recordsReader = new RecordsReader(filePath , "Kwik-E-Mart", Constants.joda.parseDateTime("20/08/2018 13:00:00"),
                Constants.joda.parseDateTime("20/08/2018 12:00:00"));

        Map<String, Transaction> actualMap = recordsReader.readValidData();

        assertEquals(actualMap.entrySet().size(), 0);
    }

    @Test
    public void readValidDataFromMillionTransactions() throws IOException {
        String filePath = classLoader.getResource(resourceRoot + "million.csv").getFile();
        writeLargeNumberOfRecords(filePath,1000000);


        recordsReader = new RecordsReader(filePath , "Kwik-E-Mart", Constants.joda.parseDateTime("20/08/2018 13:00:00"),
                Constants.joda.parseDateTime("20/08/2018 12:00:00"));

        Map<String, Transaction> actualMap = recordsReader.readValidData();

        assertEquals(actualMap.entrySet().size(), 1);
        assertEquals(Iterables.getOnlyElement(actualMap.values()),
                Transaction.normalTransaction("WLMFRDGD",Constants.joda.parseDateTime("20/08/2018 12:45:33"), 59.99, "KWIK-E-MART"));

        deleteFileContent(filePath);
    }

    private void deleteFileContent(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("");
        writer.close();
    }

    private void writeLargeNumberOfRecords(String filePath, int numberOfRecords) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("ID, Date, Amount, Merchant, Type, Related Transaction");
        writer.newLine();
        for (int i = 0; i < numberOfRecords; i++) {
            String row = "id_"+i+", 20/08/2018 12:45:33, 59.99, abc, PAYMENT,";
            writer.write(row);
            writer.newLine();
        }
        //validData
        writer.write("WLMFRDGD, 20/08/2018 12:45:33, 59.99, KWIK-E-MART, PAYMENT,\n" +
                "YGXKOEIA, 20/08/2018 12:46:17, 10.95, Kwik-E-Mart, PAYMENT,\n" +
                "SUOVOISP, 20/08/2018 13:12:22, 5.00, Kwik-E-Mart, PAYMENT,\n" +
                "AKNBVHMN, 20/08/2018 13:14:11, 10.95, Kwik-E-Mart, REVERSAL, YGXKOEIA\n" +
                "LFVCTEYM, 20/08/2018 12:50:02, 5.00, MacLaren, PAYMENT,\n" +
                "JYAPKZFZ, 20/08/2018 14:07:10, 99.50, MacLaren, PAYMENT,");
        writer.close();
    }


}