package com.abs.dao;

import com.abs.entity.WalletEntity;

import java.util.List;

public interface WalletDaoApi {

    void createWallet(WalletEntity entity);
    List<WalletEntity> fetchAllCustomerWallets(Integer idCustomer);
}
