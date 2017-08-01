package com.abs.entity;

import javax.persistence.*;

@Entity
@Table(name = "WALLET")
public class WalletEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "customer_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "WALLET_ADDRESS")
    private String walletAddress;

    @Column(name = "WALLET_ALIAS")
    private String walletAlias;

    @Column(name = "WALLET_PASSPHRASE")
    private String walletPassphrase;

    @Column(name = "id_customer")
    private Integer idCustomer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getWalletAlias() {
        return walletAlias;
    }

    public void setWalletAlias(String walletAlias) {
        this.walletAlias = walletAlias;
    }

    public String getWalletPassphrase() {
        return walletPassphrase;
    }

    public void setWalletPassphrase(String walletPassphrase) {
        this.walletPassphrase = walletPassphrase;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }
}