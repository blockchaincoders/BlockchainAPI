package com.abs.entity;

public class BarChartBlockDifficultyData {

    String blockNo;
    int difficultyLevel;

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public static String getXKey(){
        return "blockNo";
    }

    public static String [] getYKeys(){
        return new String[]{"difficultyLevel"};
    }

    public static String [] getLabels(){
        return new String[]{"Difficulty Level"};
    }
}
