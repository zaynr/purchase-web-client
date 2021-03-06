package me.zengzy.dto;

import me.zengzy.entity.AllAddons;
import me.zengzy.entity.FilterDict;

import java.util.ArrayList;

public class PurOrderBean {
    private int purSerialNo;
    private int orderStatusNo;
    private int contractSerialNo;
    private String purchaserName;
    private String providerName;
    private String orderStatus;
    private String typeContent;
    private double orderAmount;
    private double expectPrice;
    private double offeredPrice;
    private int typeNo;
    private String typeUnit;
    private String moreDetail, addonNum;
    private ArrayList<FilterDict> filters;
    private ArrayList<AllAddons> addons;
    private int showExpectPrice;
    private int pageSize, pageIndex;
    private String datetime;
    private String addonUrl;
    private String expireDate;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getAddonUrl() {
        return addonUrl;
    }

    public void setAddonUrl(String addonUrl) {
        this.addonUrl = addonUrl;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getShowExpectPrice() {
        return showExpectPrice;
    }

    public void setShowExpectPrice(int showExpectPrice) {
        this.showExpectPrice = showExpectPrice;
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

    public int getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(int typeNo) {
        this.typeNo = typeNo;
    }

    public String getTypeUnit() {
        return typeUnit;
    }

    public void setTypeUnit(String typeUnit) {
        this.typeUnit = typeUnit;
    }

    public int getPurSerialNo() {
        return purSerialNo;
    }

    public void setPurSerialNo(int purSerialNo) {
        this.purSerialNo = purSerialNo;
    }

    public int getOrderStatusNo() {
        return orderStatusNo;
    }

    public void setOrderStatusNo(int orderStatusNo) {
        this.orderStatusNo = orderStatusNo;
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

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(double expectPrice) {
        this.expectPrice = expectPrice;
    }

    public double getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(double offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public String getMoreDetail() {
        return moreDetail;
    }

    public void setMoreDetail(String moreDetail) {
        this.moreDetail = moreDetail;
    }

    public String getAddonNum() {
        return addonNum;
    }

    public void setAddonNum(String addonNum) {
        this.addonNum = addonNum;
    }

    public ArrayList<FilterDict> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<FilterDict> filters) {
        this.filters = filters;
    }

    public ArrayList<AllAddons> getAddons() {
        return addons;
    }

    public void setAddons(ArrayList<AllAddons> addons) {
        this.addons = addons;
    }
}
