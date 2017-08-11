package com.abs.entity;

public class TransactionHistory {

    String txnDate;
    String fromAccAlias;
    String fromAccAddress;
    String toAccAlias;
    String toAccAddress;
    String amount;
    String status;

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

    public String getFromAccAddress() {
        return fromAccAddress;
    }

    public void setFromAccAddress(String fromAccAddress) {
        this.fromAccAddress = fromAccAddress;
    }

    public String getToAccAddress() {
        return toAccAddress;
    }

    public void setToAccAddress(String toAccAddress) {
        this.toAccAddress = toAccAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
