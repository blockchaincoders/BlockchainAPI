package com.abs.entity;

public class AreaChartMobileData {

    String period;
    Integer iphone;
    Integer ipad;
    Integer itouch;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getIphone() {
        return iphone;
    }

    public void setIphone(Integer iphone) {
        this.iphone = iphone;
    }

    public Integer getIpad() {
        return ipad;
    }

    public void setIpad(Integer ipad) {
        this.ipad = ipad;
    }

    public Integer getItouch() {
        return itouch;
    }

    public void setItouch(Integer itouch) {
        this.itouch = itouch;
    }

    public static String getXKey(){
       return "period";
    }

    public static String [] getYKeys(){
        return new String[]{"iphone","ipad","itouch"};
    }

    public static String [] getLabels(){
        return new String[]{"iPhone","iPad","iPod Touch"};
    }
}
