package me.zengzy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "pur_orders")
@Table(name = "pur_orders")
public class PurOrders {
    @Id
    @Column(name = "pur_serial_no")
    private int purSerialNo;
    @Column(name = "purchaser_name")
    private String purchaserName;
    @Column(name = "order_status")
    private int orderStatus;
    @Column(name = "type_no")
    private int typeNo;
    private double expect_price;
    private double order_amount;
    private String more_detail;
    private String filter_dict;
    private int expect_status;

    public int getExpect_status() {
        return expect_status;
    }

    public void setExpect_status(int expect_status) {
        this.expect_status = expect_status;
    }

    public String getFilter_dict() {
        return filter_dict;
    }

    public void setFilter_dict(String filter_dict) {
        this.filter_dict = filter_dict;
    }

    public String getMore_detail() {
        return more_detail;
    }

    public void setMore_detail(String more_detail) {
        this.more_detail = more_detail;
    }

    public double getExpect_price() {
        return expect_price;
    }

    public void setExpect_price(double expect_price) {
        this.expect_price = expect_price;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }

    public int getPurSerialNo() {
        return purSerialNo;
    }

    public void setPurSerialNo(int purSerialNo) {
        this.purSerialNo = purSerialNo;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(int typeNo) {
        this.typeNo = typeNo;
    }
}
