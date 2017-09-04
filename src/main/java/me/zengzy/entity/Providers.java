package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "providers")
@Table(name = "providers")
public class Providers {
    @Id
    private String mobile_no;
    private String provide_type;

    public String getProvide_type() {
        return provide_type;
    }

    public void setProvide_type(String provide_type) {
        this.provide_type = provide_type;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

}
