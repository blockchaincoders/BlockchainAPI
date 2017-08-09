package com.abs.entity;

public class BarChartBlockTxnCountData {

    String blockNo;
    int txnCount;

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public int getTxnCount() {
        return txnCount;
    }

    public void setTxnCount(int txnCount) {
        this.txnCount = txnCount;
    }
    public static String getXKey(){
        return "blockNo";
    }

    public static String [] getYKeys(){
        return new String[]{"txnCount"};
    }

    public static String [] getLabels(){
        return new String[]{"Txn Count"};
    }
}
