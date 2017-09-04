package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity(name = "order_types")
@Table(name = "order_types")
public class OrderTypes {
    @Id
    private int type_no;
    private String type_content, type_unit;

    public String getType_unit() {
        return type_unit;
    }

    public void setType_unit(String type_unit) {
        this.type_unit = type_unit;
    }

    public int getType_no() {
        return type_no;
    }

    public void setType_no(int type_no) {
        this.type_no = type_no;
    }

    public String getType_content() {
        return type_content;
    }

    public void setType_content(String type_content) {
        this.type_content = type_content;
    }
}
