package com.abs.entity;


public class ChatDataBean {

    String element;
    Information data[];
    String xkey;
    String [] ykeys;
    String [] labels;
    Integer pointSize;
    String hideHover;
    boolean resize;


    public class  Information {
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
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public Information[] getData() {
        return data;
    }

    public void setData(Information[] data) {
        this.data = data;
    }

    public String getXkey() {
        return xkey;
    }

    public void setXkey(String xkey) {
        this.xkey = xkey;
    }

    public String[] getYkeys() {
        return ykeys;
    }

    public void setYkeys(String[] ykeys) {
        this.ykeys = ykeys;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public Integer getPointSize() {
        return pointSize;
    }

    public void setPointSize(Integer pointSize) {
        this.pointSize = pointSize;
    }

    public String getHideHover() {
        return hideHover;
    }

    public void setHideHover(String hideHover) {
        this.hideHover = hideHover;
    }

    public boolean isResize() {
        return resize;
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }
}
