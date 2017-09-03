package me.zengzy.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "providers")
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
