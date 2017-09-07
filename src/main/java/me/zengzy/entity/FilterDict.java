package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "filter_dict")
@Table(name = "filter_dict")
public class FilterDict {
    @Id
    private int serial_no;
    private int order_serial_no;
    private String province;
    private String city;
    private String dist;

    public int getOrder_serial_no() {
        return order_serial_no;
    }

    public void setOrder_serial_no(int order_serial_no) {
        this.order_serial_no = order_serial_no;
    }

    public int getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(int serial_no) {
        this.serial_no = serial_no;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }
}
