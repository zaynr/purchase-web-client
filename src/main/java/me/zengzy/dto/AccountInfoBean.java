package me.zengzy.dto;

import me.zengzy.entity.OrderTypes;
import me.zengzy.entity.UserAddress;

import java.util.ArrayList;

public class AccountInfoBean {
    private String userName, mobileNo, pwd, userType;
    private ArrayList<OrderTypes> provideType;
    private UserAddress address;
    private String spaceUsed;

    public String getSpaceUsed() {
        return spaceUsed;
    }

    public void setSpaceUsed(String spaceUsed) {
        this.spaceUsed = spaceUsed;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
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
