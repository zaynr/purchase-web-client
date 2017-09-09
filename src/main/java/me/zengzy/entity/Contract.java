package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "contracts")
@Table(name = "contracts")
public class Contract {
    @Id
    private int contract_serial_no;
    private int pro_serial_no, pur_serial_no, contract_status;
    private String sign_time;

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public int getContract_serial_no() {
        return contract_serial_no;
    }

    public void setContract_serial_no(int contract_serial_no) {
        this.contract_serial_no = contract_serial_no;
    }

    public int getPro_serial_no() {
        return pro_serial_no;
    }

    public void setPro_serial_no(int pro_serial_no) {
        this.pro_serial_no = pro_serial_no;
    }

    public int getPur_serial_no() {
        return pur_serial_no;
    }

    public void setPur_serial_no(int pur_serial_no) {
        this.pur_serial_no = pur_serial_no;
    }

    public int getContract_status() {
        return contract_status;
    }

    public void setContract_status(int contract_status) {
        this.contract_status = contract_status;
    }

}
