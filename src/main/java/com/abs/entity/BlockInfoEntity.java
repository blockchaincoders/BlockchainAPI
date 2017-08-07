package com.abs.entity;

import javax.persistence.*;

@Entity
@Table(name = "BLOCK_INFO")
public class BlockInfoEntity {

    @Id
    @Column(name = "ID_GENERATED", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "customer_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "BLOCK_NUMBER")
    private String number;

    @Column(name = "TRANSACTION_COUNT")
    private String transactionSize;

    @Column(name = "HASH")
    private String hash;

    @Column(name = "PARENT_HASH")
    private String parentHash;

    @Column(name = "NONCE")
    private String nonceRaw;

    @Column(name = "TIMESTAMP")
    private String timeStamp;

    @Column(name = "MINER")
    private String miner;

    @Column(name = "DIFFICULTY")
    private String difficulty;

    @Column(name = "GAS_LIMIT")
    private String gasLimit;

    @Column(name = "GAS_USED")
    private String gasUsed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTransactionSize() {
        return transactionSize;
    }

    public void setTransactionSize(String transactionSize) {
        this.transactionSize = transactionSize;
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

    public String getNonceRaw() {
        return nonceRaw;
    }

    public void setNonceRaw(String nonceRaw) {
        this.nonceRaw = nonceRaw;
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
}
