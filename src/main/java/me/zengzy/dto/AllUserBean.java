package me.zengzy.dto;

public class AllUserBean {
    private String mobileNo;
    private int userType;
    private String userName;
    private double spaceUsed;
    private int pageSize;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getSpaceUsed() {
        return spaceUsed;
    }

    public void setSpaceUsed(double spaceUsed) {
        this.spaceUsed = spaceUsed;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
