package me.zengzy.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pro_orders")
public class ProOrders {
    @Id
    private int pur_serial_no;
    private String purchaser_name;
    private int order_status, type_no;

    public int getPur_serial_no() {
        return pur_serial_no;
    }

    public void setPur_serial_no(int pur_serial_no) {
        this.pur_serial_no = pur_serial_no;
    }

    public String getPurchaser_name() {
        return purchaser_name;
    }

    public void setPurchaser_name(String purchaser_name) {
        this.purchaser_name = purchaser_name;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getType_no() {
        return type_no;
    }

    public void setType_no(int type_no) {
        this.type_no = type_no;
    }
}
