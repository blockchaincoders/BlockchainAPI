package com.abs.entity;

public class AreaChartBlockCreationTimeData {

    String blockNo;
    String creationTime;

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public static String getXKey(){
        return "blockNo";
    }

    public static String [] getYKeys(){
        return new String[]{"creationTime"};
    }

    public static String [] getLabels(){
        return new String[]{"Block Creation Time"};
    }
}
