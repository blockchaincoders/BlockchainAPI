package com.abs.entity;

public class TransactionHistory {

    String txnType;
    String fromAccAlias;
    String toAccAlias;
    String amount;

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
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
