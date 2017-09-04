package me.zengzy.dto;

public class PurOrderBean {
    private int purSerialNo;
    private int orderStatusNo;
    private int contractSerialNo;
    private String purchaserName;
    private String providerName;
    private String orderStatus;
    private String typeContent;
    private String orderAmount;
    private String expectPrice;

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(String expectPrice) {
        this.expectPrice = expectPrice;
    }

    public int getOrderStatusNo() {
        return orderStatusNo;
    }

    public void setOrderStatusNo(int orderStatusNo) {
        this.orderStatusNo = orderStatusNo;
    }

    public int getPurSerialNo() {
        return purSerialNo;
    }

    public void setPurSerialNo(int purSerialNo) {
        this.purSerialNo = purSerialNo;
    }

    public int getContractSerialNo() {
        return contractSerialNo;
    }

    public void setContractSerialNo(int contractSerialNo) {
        this.contractSerialNo = contractSerialNo;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTypeContent() {
        return typeContent;
    }

    public void setTypeContent(String typeContent) {
        this.typeContent = typeContent;
    }
}
