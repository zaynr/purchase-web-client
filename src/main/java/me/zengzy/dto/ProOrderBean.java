package me.zengzy.dto;

public class ProOrderBean {
    private int proSerialNo;
    private int purSerialNo;
    private String providerMobileNo;
    private String purAddress;
    private String offerPrice;
    private int orderTypeNo;
    private String orderStatus;
    private String expressNo;
    private int pageSize, pageIndex;
    private String datetime;
    private String purchaserMobileNo;
    private String orderAmount;
    private String orderType;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPurchaserMobileNo() {
        return purchaserMobileNo;
    }

    public void setPurchaserMobileNo(String purchaserMobileNo) {
        this.purchaserMobileNo = purchaserMobileNo;
    }

    public String getPurAddress() {
        return purAddress;
    }

    public void setPurAddress(String purAddress) {
        this.purAddress = purAddress;
    }

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
