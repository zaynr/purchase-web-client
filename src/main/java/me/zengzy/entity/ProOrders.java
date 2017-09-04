package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "pro_orders")
@Table(name = "pro_orders")
public class ProOrders {
    @Id
    private int pro_serial_no;
    private String provider_name;
    private int order_status, pur_serial_no;
    private double offer_price;
    private String express_no;

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public double getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(double offer_price) {
        this.offer_price = offer_price;
    }

    public int getPro_serial_no() {
        return pro_serial_no;
    }

    public void setPro_serial_no(int pro_serial_no) {
        this.pro_serial_no = pro_serial_no;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getPur_serial_no() {
        return pur_serial_no;
    }

    public void setPur_serial_no(int pur_serial_no) {
        this.pur_serial_no = pur_serial_no;
    }
}
