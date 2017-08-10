package com.abs.dao;

import com.abs.entity.CustomerEntity;
import com.abs.entity.TransactionHistoryEntity;

import java.util.List;

public interface TransactionHistoryDaoApi {

    void addTxnHistory(TransactionHistoryEntity entity);
    List<TransactionHistoryEntity> fetchAllTxnHistoryByCustomerId(Integer customerId);

}