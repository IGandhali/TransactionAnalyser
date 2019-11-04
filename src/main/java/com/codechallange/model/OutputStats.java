package com.codechallange.model;

public class OutputStats {
    private long totalTransactionCount;
    private double avgAmount;

    public OutputStats(long totalTransactionCount, double avgAmount) {
        this.totalTransactionCount = totalTransactionCount;
        this.avgAmount = avgAmount;
    }

    public static OutputStats errorStats(){
        return new OutputStats(-1L,-0.1);
    }

    public long getTotalTransactionCount() {
        return totalTransactionCount;
    }

    public double getAvgAmount() {
        return avgAmount;
    }

    @Override
    public String toString() {
        return "OutputStats{" +
                "total transaction count=" + totalTransactionCount +
                ", avg amount spent=" + avgAmount +
                '}';
    }
}
