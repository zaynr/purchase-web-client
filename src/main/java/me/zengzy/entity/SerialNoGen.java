package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "serial_no_gen")
@Table(name = "serial_no_gen")
public class SerialNoGen {
    @Id
    private int serial_no;

    public int getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(int serial_no) {
        this.serial_no = serial_no;
    }
}
