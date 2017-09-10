package me.zengzy.dto;

public class ContactsBean {
    private int serial_no;
    private String purchaser_mobile_no, provider_mobile_no;
    private int userType;
    private int coop_count, pageSize;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(int serial_no) {
        this.serial_no = serial_no;
    }

    public String getPurchaser_mobile_no() {
        return purchaser_mobile_no;
    }

    public void setPurchaser_mobile_no(String purchaser_mobile_no) {
        this.purchaser_mobile_no = purchaser_mobile_no;
    }

    public String getProvider_mobile_no() {
        return provider_mobile_no;
    }

    public void setProvider_mobile_no(String provider_mobile_no) {
        this.provider_mobile_no = provider_mobile_no;
    }

    public int getCoop_count() {
        return coop_count;
    }

    public void setCoop_count(int coop_count) {
        this.coop_count = coop_count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
