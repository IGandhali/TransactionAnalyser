package com.codechallange.model;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

public class Transaction {
    private String id;
    private DateTime dateTime;
    private double amount;
    private String merchant;
    private Type type;
    private String reverseTransactionId;

    public Transaction(String id, DateTime time, double amount, String merchant, Type type, String reverseTransactionId) {
        this.id = id;
        this.dateTime = time;
        this.amount = amount;
        this.merchant = merchant;
        this.reverseTransactionId = reverseTransactionId;
        this.type = type;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getReverseTransactionId() {
        return reverseTransactionId;
    }

    public static Transaction normalTransaction(String id, DateTime dateTime, double amount, String merchant) {
        return new Transaction(id, dateTime, amount, merchant, Type.PAYMENT, null);
    }

    public static Transaction normalTransaction(String id, DateTime dateTime, double amount, String merchant, String reverseTransactionId) {
        return new Transaction(id, dateTime, amount, merchant, Type.REVERSAL, reverseTransactionId);
    }

    public enum Type {
        PAYMENT,
        REVERSAL
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                Objects.equal(id, that.id) &&
                Objects.equal(dateTime, that.dateTime) &&
                Objects.equal(merchant, that.merchant) &&
                type == that.type &&
                Objects.equal(reverseTransactionId, that.reverseTransactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, dateTime, amount, merchant, type, reverseTransactionId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", merchant='" + merchant + '\'' +
                ", type=" + type +
                ", reverseTransactionId='" + reverseTransactionId + '\'' +
                '}';
    }
}
