package me.zengzy.dto;

public class ProOrderBean {
    private int proSerialNo;
    private String offerPrice;
    private String providerMobileNo;
    private int purSerialNo;
    private String orderAmount;
    private String orderType;
    private int orderTypeNo;
    private String orderStatus;
    private String expressNo;

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public int getProSerialNo() {
        return proSerialNo;
    }

    public void setProSerialNo(int proSerialNo) {
        this.proSerialNo = proSerialNo;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getProviderMobileNo() {
        return providerMobileNo;
    }

    public void setProviderMobileNo(String providerMobileNo) {
        this.providerMobileNo = providerMobileNo;
    }

    public int getPurSerialNo() {
        return purSerialNo;
    }

    public void setPurSerialNo(int purSerialNo) {
        this.purSerialNo = purSerialNo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getOrderTypeNo() {
        return orderTypeNo;
    }

    public void setOrderTypeNo(int orderTypeNo) {
        this.orderTypeNo = orderTypeNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
