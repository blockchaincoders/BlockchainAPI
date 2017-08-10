package com.abs.service;

import com.abs.entity.TransactionHistoryEntity;

import java.util.List;

public interface TransactionHistoryServiceApi {

    void addTxnHistory(TransactionHistoryEntity entity);
    List<TransactionHistoryEntity> fetchAllTxnHistoryByCustomerId(Integer customerId);

}