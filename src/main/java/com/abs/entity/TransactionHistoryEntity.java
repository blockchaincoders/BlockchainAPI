package com.abs.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION_HISTORY")
public class TransactionHistoryEntity {

    @Id
    @Column(name = "ID_GENERATED", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "SEQ_TXN_HISTORY", allocationSize = 1)
    private Integer id;

    @Column(name = "ID_CUSTOMER")
    private Integer customerId;

    @Column(name = "ID_TXN")
    private String txnId;

    @Column(name = "TXN_STATUS")
    private String txnStatus;

    @Column(name = "FROM_ACCOUNT_ALIAS")
    private String fromAccAlias;

    @Column(name = "FROM_ACCOUNT_ADDRESS")
    private String fromAccAddress;

    @Column(name = "TO_ACCOUNT_ALIAS")
    private String toAccAlias;

    @Column(name = "TO_ACCOUNT_ADDRESS")
    private String toAccAddress;

    @Column(name = "TXN_AMOUNT")
    private String txnAmount;

    @Column(name = "TXN_FEE")
    private String txnFee;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TXN_DATE")
    private Date txnDate;

    public TransactionHistoryEntity(){
        setTxnDate(new Date());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxnId() {
        return txnId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getFromAccAlias() {
        return fromAccAlias;
    }

    public void setFromAccAlias(String fromAccAlias) {
        this.fromAccAlias = fromAccAlias;
    }

    public String getFromAccAddress() {
        return fromAccAddress;
    }

    public void setFromAccAddress(String fromAccAddress) {
        this.fromAccAddress = fromAccAddress;
    }

    public String getToAccAlias() {
        return toAccAlias;
    }

    public void setToAccAlias(String toAccAlias) {
        this.toAccAlias = toAccAlias;
    }

    public String getToAccAddress() {
        return toAccAddress;
    }

    public void setToAccAddress(String toAccAddress) {
        this.toAccAddress = toAccAddress;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getTxnFee() {
        return txnFee;
    }

    public void setTxnFee(String txnFee) {
        this.txnFee = txnFee;
    }

    public Date getTxnDate() {

        if (this.txnDate == null) {
            return null;
        }
        return new Date(this.txnDate.getTime());
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }
}