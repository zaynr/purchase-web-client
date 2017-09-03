package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "purchasers")
@Table(name = "purchasers")
public class Purchasers {
    @Id
    private String mobile_no;
    private String perfer_type;

    public String getPerfer_type() {
        return perfer_type;
    }

    public void setPerfer_type(String perfer_type) {
        this.perfer_type = perfer_type;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
