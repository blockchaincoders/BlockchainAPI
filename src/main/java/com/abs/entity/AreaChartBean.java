package com.abs.entity;


public class AreaChartBean {

    String element;
    Object data[];
    String xkey;
    String [] ykeys;
    String [] labels;
    Integer pointSize;
    String hideHover;
    boolean resize;



    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
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

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }
}
