package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "message_type")
@Table(name = "message_type")
public class MessageType {
    @Id
    private int type_no;
    private String type_describe;
    private int receiver;

    public int getType_no() {
        return type_no;
    }

    public void setType_no(int type_no) {
        this.type_no = type_no;
    }

    public String getType_describe() {
        return type_describe;
    }

    public void setType_describe(String type_describe) {
        this.type_describe = type_describe;
    }
}
