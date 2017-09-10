package me.zengzy.dto;

public class ContactDetailBean {
    private int contractSn;
    private String orderType;
    private String datetime;
    private String addonUrl;
    private String orderAmount;;
    private String orderPrice;

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getContractSn() {
        return contractSn;
    }

    public void setContractSn(int contractSn) {
        this.contractSn = contractSn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAddonUrl() {
        return addonUrl;
    }

    public void setAddonUrl(String addonUrl) {
        this.addonUrl = addonUrl;
    }
}
