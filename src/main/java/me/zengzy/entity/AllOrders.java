package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "all_orders")
@Table(name = "all_orders")
public class AllOrders {
    @Id
    private int serial_no;
    private String mobile_no;
    private int order_status;
    private int order_cat;
    private int read_flag;
    private String gen_date;

    public String getGen_date() {
        return gen_date;
    }

    public void setGen_date(String gen_date) {
        this.gen_date = gen_date;
    }

    public int getRead_flag() {
        return read_flag;
    }

    public void setRead_flag(int read_flag) {
        this.read_flag = read_flag;
    }

    public int getOrder_cat() {
        return order_cat;
    }

    public void setOrder_cat(int order_cat) {
        this.order_cat = order_cat;
    }

    public int getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(int serial_no) {
        this.serial_no = serial_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }
}
