package com.abs.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imran.khan on 8/3/2017.
 */
public class BlockInfoBean {
    private String number;
    private String hash;
    private String parentHash;
    private String transactionSize;
    private String transactionsRoot;
    private String timeStamp;
    private String miner;
    private String extraData;
    private String nonceRaw;
    private String difficulty;
    private String gasLimit;
    private String gasUsed;
    private String logsBloom;
    private String mixHash;
    private String receiptsRoot;
    private String sizeRaw;
    private List<String> transactionResults = new ArrayList<String>();

    public List<String> getTransactionResults() {
        return transactionResults;
    }

    public void setTransactionResults(List<String> transactionResults) {
        this.transactionResults = transactionResults;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getParentHash() {
        return parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public String getTransactionSize() {
        return transactionSize;
    }

    public void setTransactionSize(String transactionSize) {
        this.transactionSize = transactionSize;
    }

    public String getTransactionsRoot() {
        return transactionsRoot;
    }

    public void setTransactionsRoot(String transactionsRoot) {
        this.transactionsRoot = transactionsRoot;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMiner() {
        return miner;
    }

    public void setMiner(String miner) {
        this.miner = miner;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getNonceRaw() {
        return nonceRaw;
    }

    public void setNonceRaw(String nonceRaw) {
        this.nonceRaw = nonceRaw;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom) {
        this.logsBloom = logsBloom;
    }

    public String getMixHash() {
        return mixHash;
    }

    public void setMixHash(String mixHash) {
        this.mixHash = mixHash;
    }

    public String getReceiptsRoot() {
        return receiptsRoot;
    }

    public void setReceiptsRoot(String receiptsRoot) {
        this.receiptsRoot = receiptsRoot;
    }

    public String getSizeRaw() {
        return sizeRaw;
    }

    public void setSizeRaw(String sizeRaw) {
        this.sizeRaw = sizeRaw;
    }


}
