package me.zengzy.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "user_address")
@IdClass(AddressPrimaryKeys.class)
@Table(name = "user_address")
public class UserAddress implements Serializable {
    @Id
    private String mobile_no;
    @Id
    private int user_type;
    private String province;
    private String city;
    private String dist;
    private String detail_address;

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }
}

class AddressPrimaryKeys implements Serializable{
    private String mobile_no;
    private int user_type;
}
