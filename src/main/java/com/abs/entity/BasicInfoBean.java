package com.abs.entity;

/**
 * Created by imran.khan on 8/11/2017.
 */
public class BasicInfoBean {

    private String web3ClientVersion;
    private String netVersion;
    private String peerCount;
    private String ethCoinBase;
    private String ethMining;
    private String ethProtocol;


    public String getWeb3ClientVersion() {
        return web3ClientVersion;
    }

    public void setWeb3ClientVersion(String web3ClientVersion) {
        this.web3ClientVersion = web3ClientVersion;
    }

    public String getNetVersion() {
        return netVersion;
    }

    public void setNetVersion(String netVersion) {
        this.netVersion = netVersion;
    }

    public String getPeerCount() {
        return peerCount;
    }

    public void setPeerCount(String peerCount) {
        this.peerCount = peerCount;
    }

    public String getEthCoinBase() {
        return ethCoinBase;
    }

    public void setEthCoinBase(String ethCoinBase) {
        this.ethCoinBase = ethCoinBase;
    }

    public String getEthMining() {
        return ethMining;
    }

    public void setEthMining(String ethMining) {
        this.ethMining = ethMining;
    }

    public String getEthProtocol() {
        return ethProtocol;
    }

    public void setEthProtocol(String ethProtocol) {
        this.ethProtocol = ethProtocol;
    }

}
