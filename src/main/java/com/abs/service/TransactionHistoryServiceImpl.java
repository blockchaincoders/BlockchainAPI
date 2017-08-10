package com.abs.service;

import com.abs.dao.TransactionHistoryDaoApi;
import com.abs.entity.TransactionHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryServiceApi{

    @Autowired
    private TransactionHistoryDaoApi transactionHistoryDao;

    @Transactional
    @Override
    public void addTxnHistory(TransactionHistoryEntity entity) {
        transactionHistoryDao.addTxnHistory(entity);
    }

    @Transactional
    @Override
    public List<TransactionHistoryEntity> fetchAllTxnHistoryByCustomerId(Integer customerId) {
        return transactionHistoryDao.fetchAllTxnHistoryByCustomerId(customerId);
    }
}
