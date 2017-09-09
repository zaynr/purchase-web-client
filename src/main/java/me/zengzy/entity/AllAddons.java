package me.zengzy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "all_addons")
@Table(name = "all_addons")
public class AllAddons {
    @Id
    private int addon_serial_no;
    private int order_serial_no;
    private String addon_url;
    private double file_size;
    private String file_key;
    private String file_name;
    private String uploader_moble_no;

    public String getUploader_moble_no() {
        return uploader_moble_no;
    }

    public void setUploader_moble_no(String uploader_moble_no) {
        this.uploader_moble_no = uploader_moble_no;
    }

    public String getFile_key() {
        return file_key;
    }

    public void setFile_key(String file_key) {
        this.file_key = file_key;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public double getFile_size() {
        return file_size;
    }

    public void setFile_size(double file_size) {
        this.file_size = file_size;
    }

    public int getAddon_serial_no() {
        return addon_serial_no;
    }

    public void setAddon_serial_no(int addon_serial_no) {
        this.addon_serial_no = addon_serial_no;
    }

    public int getOrder_serial_no() {
        return order_serial_no;
    }

    public void setOrder_serial_no(int order_serial_no) {
        this.order_serial_no = order_serial_no;
    }

    public String getAddon_url() {
        return addon_url;
    }

    public void setAddon_url(String addon_url) {
        this.addon_url = addon_url;
    }
}
