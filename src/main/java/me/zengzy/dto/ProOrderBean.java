package me.zengzy.dto;

public class ProOrderBean {
    private int proSerialNo;
    private String providerMobileNo;
    private int order_status;
    private double offer_price;
    private int purSerialNo;

    public int getProSerialNo() {
        return proSerialNo;
    }

    public void setProSerialNo(int proSerialNo) {
        this.proSerialNo = proSerialNo;
    }

    public String getProviderMobileNo() {
        return providerMobileNo;
    }

    public void setProviderMobileNo(String providerMobileNo) {
        this.providerMobileNo = providerMobileNo;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public double getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(double offer_price) {
        this.offer_price = offer_price;
    }

    public int getPurSerialNo() {
        return purSerialNo;
    }

    public void setPurSerialNo(int purSerialNo) {
        this.purSerialNo = purSerialNo;
    }
}
