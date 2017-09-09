package me.zengzy.dto;

import java.util.ArrayList;

public class OrderCategoryBean {
    private String name;
    private ArrayList<OrderTypeBean> types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<OrderTypeBean> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<OrderTypeBean> types) {
        this.types = types;
    }
}
