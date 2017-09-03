package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "providers")
@Table(name = "providers")
public class Providers {
    @Id
    private String mobile_no;
    private String privide_type;

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getPrivide_type() {
        return privide_type;
    }

    public void setPrivide_type(String privide_type) {
        this.privide_type = privide_type;
    }
}
