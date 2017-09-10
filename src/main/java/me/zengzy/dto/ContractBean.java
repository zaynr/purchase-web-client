package me.zengzy.dto;

public class ContractBean {
    private int pageSize;
    private int contractSn;
    private int proOrdSn;
    private int purOrdSn;
    private int userType;
    private int status;
    private String addonUrl;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getContractSn() {
        return contractSn;
    }

    public void setContractSn(int contractSn) {
        this.contractSn = contractSn;
    }

    public int getProOrdSn() {
        return proOrdSn;
    }

    public void setProOrdSn(int proOrdSn) {
        this.proOrdSn = proOrdSn;
    }

    public int getPurOrdSn() {
        return purOrdSn;
    }

    public void setPurOrdSn(int purOrdSn) {
        this.purOrdSn = purOrdSn;
    }

    public String getAddonUrl() {
        return addonUrl;
    }

    public void setAddonUrl(String addonUrl) {
        this.addonUrl = addonUrl;
    }
}
