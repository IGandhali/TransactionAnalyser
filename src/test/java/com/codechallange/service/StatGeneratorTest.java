package com.codechallange.service;

import com.codechallange.model.OutputStats;
import com.codechallange.model.Transaction;
import com.codechallange.util.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StatGeneratorTest {
    private StatGenerator statGenerator = new StatGenerator();

    @Before
    public void setUp() {
    }

    @Test
    public void generateStatsForEmptyMap() {
        OutputStats outputStats = statGenerator.generateStats(Collections.emptyMap());
        Assert.assertEquals(0, outputStats.getTotalTransactionCount());
        Assert.assertEquals(0.0, outputStats.getAvgAmount(), 0.0);
    }

    @Test
    public void generateStatsForValidMap() {
        Map<String, Transaction> transactions = mockReadTransactions(5);
        OutputStats outputStats = statGenerator.generateStats(transactions);
        Assert.assertEquals(5, outputStats.getTotalTransactionCount());
        Assert.assertEquals(2.0, outputStats.getAvgAmount(), 0.0);
    }

    @Test
    public void generateStatsForValidMapOfMillionTransctions() {
        Map<String, Transaction> transactions = mockReadTransactions(1000000);
        OutputStats outputStats = statGenerator.generateStats(transactions);
        Assert.assertEquals(1000000, outputStats.getTotalTransactionCount());
        Assert.assertEquals(499999.5, outputStats.getAvgAmount(), 0.0);
    }

    @Test
    public void generateStatsForValidMapOfTenMillionTransctions() {
        Map<String, Transaction> transactions = mockReadTransactions(10000000);
        OutputStats outputStats = statGenerator.generateStats(transactions);
        Assert.assertEquals(10000000, outputStats.getTotalTransactionCount());
        Assert.assertEquals(4999999.5, outputStats.getAvgAmount(), 0.0);
    }

    private Map<String, Transaction> mockReadTransactions(int numOfTransactions) {
        Map<String, Transaction> dummyMap = new HashMap<>();
        for (int i = 0; i < numOfTransactions; i++) {
            String id = "id_"+i;
            dummyMap.put(id, Transaction.normalTransaction(id, Constants.joda.parseDateTime("11/12/2019 12:00:05"), i, "merchantName"));
        }
        return dummyMap;
    }
}