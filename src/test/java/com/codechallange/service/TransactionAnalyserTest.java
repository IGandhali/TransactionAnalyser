package com.codechallange.service;

import com.codechallange.model.OutputStats;
import com.codechallange.model.Transaction;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionAnalyserTest {

    class DummyRecordReader extends RecordsReader{

        DummyRecordReader() {
            super("dummyFilePath", "MerchantnameToCheck", DateTime.now(), DateTime.now());
        }

        @Override
        Map<String, Transaction> readValidData() {
            return new HashMap<>();
        }
    }

    class DummyStatsGenerator extends StatGenerator{
        @Override
        OutputStats generateStats(Map<String, Transaction> transactions) {
            assertTrue(transactions.isEmpty());
            return new OutputStats(1L,0.1);
        }
    }

    private class DummyRecordReaderThrowingError extends RecordsReader {
        DummyRecordReaderThrowingError() {
            super("dummyFilePath", "MerchantnameToCheck", DateTime.now(), DateTime.now());
        }

        @Override
        Map<String, Transaction> readValidData() throws IOException {
            throw new IOException("dummy");
        }
    }

    @Test
    public void analyse() {
        TransactionAnalyser analyser = new TransactionAnalyser(new DummyRecordReader(),new DummyStatsGenerator());
        OutputStats outputStats = analyser.analyse();
        assertEquals(outputStats.getTotalTransactionCount(),1);
        Assert.assertEquals(outputStats.getAvgAmount(),0.1,0.0);
    }

    @Test
    public void analyseErrorOutput() {
        TransactionAnalyser analyser = new TransactionAnalyser(new DummyRecordReaderThrowingError(),new DummyStatsGenerator());
        OutputStats outputStats = analyser.analyse();
        assertEquals(outputStats.getTotalTransactionCount(),-1);
        Assert.assertEquals(outputStats.getAvgAmount(),-0.1,0.0);
    }
}