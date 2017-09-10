package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "message")
@Table(name = "message")
@IdClass(MessagePrimaryKeys.class)
public class Message implements Serializable {
    @Id
    private String mobile_no;
    @Id
    private int user_type;
    @Id
    private int message_type_no;
    private int message_cnt;

    public int getMessage_cnt() {
        return message_cnt;
    }

    public void setMessage_cnt(int message_cnt) {
        this.message_cnt = message_cnt;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getMessage_type_no() {
        return message_type_no;
    }

    public void setMessage_type_no(int message_type_no) {
        this.message_type_no = message_type_no;
    }
}
class MessagePrimaryKeys implements Serializable{
    private String mobile_no;
    private int user_type;
    private int message_type_no;
}
