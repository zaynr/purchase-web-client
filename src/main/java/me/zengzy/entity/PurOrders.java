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
