package com.abs.dao;

import com.abs.entity.WalletEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WalletDaoImpl implements WalletDaoApi {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createWallet(WalletEntity entity) {
        this.sessionFactory.getCurrentSession().save(entity);
    }

    public List<WalletEntity> fetchAllCustomerWallets(Integer idCustomer) {
        String query= new StringBuilder().append("from WalletEntity as we where we.idCustomer = ")
                .append(idCustomer).toString();
        return this.sessionFactory.getCurrentSession().createQuery(query).list();
    }
}
