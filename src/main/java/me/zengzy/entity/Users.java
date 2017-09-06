package me.zengzy.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(PrimaryKeys.class)
@Table(name = "users")
public class Users implements Serializable{
        
    @Id
    @Column(name = "mobile_no")
    private String mobileNo;
    @Id
    @Column(name = "user_type")
    private int userType;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "pwd")
    private String pwd;
    private double space_used;

    public double getSpace_used() {
        return space_used;
    }

    public void setSpace_used(double space_used) {
        this.space_used = space_used;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}

class PrimaryKeys implements Serializable{
    private String mobileNo;
    private int userType;
}
