package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "purchasers")
@Table(name = "purchasers")
public class Purchasers {
    @Id
    private String mobile_no;
    private String prefer_type;

    public String getPrefer_type() {
        return prefer_type;
    }

    public void setPrefer_type(String prefer_type) {
        this.prefer_type = prefer_type;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
