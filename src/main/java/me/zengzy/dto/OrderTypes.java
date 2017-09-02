package me.zengzy.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "order_types")
public class OrderTypes {
    @Id
    private int type_no;
    private String type_content;

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
