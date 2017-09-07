package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "admin_option")
@Table(name = "admin_option")
public class AdminOption {
    @Id
    private int option_no;
    private String option_describe;
    private String option_content;

    public int getOption_no() {
        return option_no;
    }

    public void setOption_no(int option_no) {
        this.option_no = option_no;
    }

    public String getOption_describe() {
        return option_describe;
    }

    public void setOption_describe(String option_describe) {
        this.option_describe = option_describe;
    }

    public String getOption_content() {
        return option_content;
    }

    public void setOption_content(String option_content) {
        this.option_content = option_content;
    }
}
