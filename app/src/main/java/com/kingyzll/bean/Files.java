package com.kingyzll.bean;

public class Files {
    private int f_id;
    private String f_title;
    private String f_content;
    private String f_date;
    private String f_publisher;
    private String f_url;
    private String f_file_list_title;
    private int f_file_list_icon;

    public int getF_file_list_icon() {
        return f_file_list_icon;
    }

    public void setF_file_list_icon(int f_file_list_icon) {
        this.f_file_list_icon = f_file_list_icon;
    }

    public String getF_file_list_title() {
        return f_file_list_title;
    }

    public void setF_file_list_title(String f_file_list_title) {
        this.f_file_list_title = f_file_list_title;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getF_title() {
        return f_title;
    }

    public void setF_title(String f_title) {
        this.f_title = f_title;
    }

    public String getF_content() {
        return f_content;
    }

    public void setF_content(String f_content) {
        this.f_content = f_content;
    }

    public String getF_date() {
        return f_date;
    }

    public void setF_date(String f_date) {
        this.f_date = f_date;
    }

    public String getF_publisher() {
        return f_publisher;
    }

    public void setF_publisher(String f_publisher) {
        this.f_publisher = f_publisher;
    }

    public String getF_url() {
        return f_url;
    }

    public void setF_url(String f_url) {
        this.f_url = f_url;
    }

    public Files(int f_id, String f_title, String f_content, String f_date, String f_publisher, String f_url) {
        this.f_id = f_id;
        this.f_title = f_title;
        this.f_content = f_content;
        this.f_date = f_date;
        this.f_publisher = f_publisher;
        this.f_url = f_url;
    }

    public Files(String f_file_list_title) {
        this.f_file_list_title = f_file_list_title;
    }

    public Files(String f_file_list_title, int f_file_list_icon) {
        this.f_file_list_title = f_file_list_title;
        this.f_file_list_icon = f_file_list_icon;
    }
}
