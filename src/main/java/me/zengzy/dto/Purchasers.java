package me.zengzy.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "purchasers")
public class Purchasers {
    @Id
    private String mobile_no;

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
