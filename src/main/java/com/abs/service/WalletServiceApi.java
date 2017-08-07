package com.abs.service;

import com.abs.entity.WalletEntity;

import java.util.List;

public interface WalletServiceApi {

    void createWallet(WalletEntity entity);
    List<WalletEntity> fetchAllCustomerWallets(Integer idCustomer);
    public WalletEntity fetchWalletDetailsByAlias(String walletAlias);
    public WalletEntity fetchWalletDetailsByAddress(String walletAddress);
}
