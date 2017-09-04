package me.zengzy.dto;

import me.zengzy.entity.OrderTypes;

import java.util.ArrayList;

public class AccountInfoBean {
    private String userName, mobileNo, pwd, userType, address;
    private ArrayList<OrderTypes> provideType;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<OrderTypes> getProvideType() {
        return provideType;
    }

    public void setProvideType(ArrayList<OrderTypes> provideType) {
        this.provideType = provideType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

}
