package com.abs.dao;

import com.abs.entity.TransactionHistoryEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionHistoryDaoImpl implements TransactionHistoryDaoApi{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTxnHistory(TransactionHistoryEntity entity) {
        this.sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public List<TransactionHistoryEntity> fetchAllTxnHistoryByCustomerId(Integer customerId) {

        String query=new StringBuilder()
                .append("from TransactionHistoryEntity as txnHistory where txnHistory.customerId = ")
                .append(customerId).append(" order by txnHistory.txnDate desc")
                .toString();
        return this.sessionFactory.getCurrentSession().createQuery(query).list();

    }
}
