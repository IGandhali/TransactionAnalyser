package com.codechallange.service;

import com.codechallange.model.OutputStats;
import com.codechallange.model.Transaction;

import java.util.DoubleSummaryStatistics;
import java.util.Map;

public class StatGenerator {

    OutputStats generateStats(Map<String, Transaction> transactions) {
        DoubleSummaryStatistics stats = calculateTransactionCountAndAvgAmount(transactions);
        return new OutputStats(stats.getCount(), stats.getAverage());
    }

    private DoubleSummaryStatistics calculateTransactionCountAndAvgAmount(Map<String, Transaction> transactions) {
        return transactions.values().stream().mapToDouble(Transaction::getAmount).summaryStatistics();
    }
}