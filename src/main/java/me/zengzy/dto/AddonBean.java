package me.zengzy.dto;

public class AddonBean{
    private int userType;
    private int addonSerialNo;
    private int orderSerialNo;
    private String addonUrl;
    private double fileSize;
    private String fileKey;
    private String fileName;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getAddonSerialNo() {
        return addonSerialNo;
    }

    public void setAddonSerialNo(int addonSerialNo) {
        this.addonSerialNo = addonSerialNo;
    }

    public int getOrderSerialNo() {
        return orderSerialNo;
    }

    public void setOrderSerialNo(int orderSerialNo) {
        this.orderSerialNo = orderSerialNo;
    }

    public String getAddonUrl() {
        return addonUrl;
    }

    public void setAddonUrl(String addonUrl) {
        this.addonUrl = addonUrl;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
