package com.abs.service;

import com.abs.entity.WalletEntity;

import java.util.List;

public interface WalletServiceApi {

    void createWallet(WalletEntity entity);
    List<WalletEntity> fetchAllCustomerWallets(Integer idCustomer);
}
