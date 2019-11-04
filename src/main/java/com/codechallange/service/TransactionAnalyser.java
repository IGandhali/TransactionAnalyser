package com.codechallange.service;

import com.codechallange.model.OutputStats;
import com.codechallange.model.Transaction;

import java.io.IOException;
import java.util.Map;

public class TransactionAnalyser {
    private RecordsReader reader;
    private StatGenerator statGenerator;

    public TransactionAnalyser(RecordsReader reader, StatGenerator statGenerator) {
        this.reader = reader;
        this.statGenerator = statGenerator;
    }

    public OutputStats analyse() {
        try {
            Map<String, Transaction> transactions = reader.readValidData();
            return statGenerator.generateStats(transactions);
        } catch (IOException e) {
            System.out.println("Issue while reading file - " + e);
            return OutputStats.errorStats();
        }
    }
}
