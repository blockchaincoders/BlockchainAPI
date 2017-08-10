package com.abs.entity;

public class TransactionHistory {

    String txnDate;
    String fromAccAlias;
    String toAccAlias;
    String amount;

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getFromAccAlias() {
        return fromAccAlias;
    }

    public void setFromAccAlias(String fromAccAlias) {
        this.fromAccAlias = fromAccAlias;
    }

    public String getToAccAlias() {
        return toAccAlias;
    }

    public void setToAccAlias(String toAccAlias) {
        this.toAccAlias = toAccAlias;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
