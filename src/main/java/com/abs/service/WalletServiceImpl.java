package com.abs.service;

import com.abs.dao.WalletDaoApi;
import com.abs.entity.WalletEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class WalletServiceImpl implements WalletServiceApi{

    @Autowired
    private WalletDaoApi walletDao;

    @Transactional
    @Override
    public void createWallet(WalletEntity entity) {
        walletDao.createWallet(entity);
    }

    @Transactional
    @Override
    public List<WalletEntity> fetchAllCustomerWallets(Integer idCustomer) {
        return walletDao.fetchAllCustomerWallets(idCustomer);
    }
}
