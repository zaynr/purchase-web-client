package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "contacts")
@Table(name = "contacts")
public class Contacts {
    @Id
    private int serial_no;
    private String purchaser_mobile_no, provider_mobile_no;
    private int coop_count;

    public int getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(int serial_no) {
        this.serial_no = serial_no;
    }

    public String getPurchaser_mobile_no() {
        return purchaser_mobile_no;
    }

    public void setPurchaser_mobile_no(String purchaser_mobile_no) {
        this.purchaser_mobile_no = purchaser_mobile_no;
    }

    public String getProvider_mobile_no() {
        return provider_mobile_no;
    }

    public void setProvider_mobile_no(String provider_mobile_no) {
        this.provider_mobile_no = provider_mobile_no;
    }

    public int getCoop_count() {
        return coop_count;
    }

    public void setCoop_count(int coop_count) {
        this.coop_count = coop_count;
    }
}
