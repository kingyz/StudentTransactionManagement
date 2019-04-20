package com.kingyzll.bean;

public class Department {

    private int dep_id;
    private String dep_title;
    private String dep_content;
    private String dep_imageId;
    private String dep_address;
    private String dep_contact;
    private String dep_email;

    public int getDep_id() {
        return dep_id;
    }

    public void setDep_id(int dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_title() {
        return dep_title;
    }

    public void setDep_title(String dep_title) {
        this.dep_title = dep_title;
    }

    public String getDep_content() {
        return dep_content;
    }

    public void setDep_content(String dep_content) {
        this.dep_content = dep_content;
    }

    public String getDep_imageId() {
        return dep_imageId;
    }

    public void setDep_imageId(String dep_imageId) {
        this.dep_imageId = dep_imageId;
    }

    public String getDep_address() {
        return dep_address;
    }

    public void setDep_address(String dep_address) {
        this.dep_address = dep_address;
    }


    public String getDep_email() {
        return dep_email;
    }

    public void setDep_email(String dep_email) {
        this.dep_email = dep_email;
    }

    public String getDep_contact() {
        return dep_contact;
    }

    public void setDep_contact(String dep_contact) {
        this.dep_contact = dep_contact;
    }

    public Department(int dep_id, String dep_title, String dep_imageId) {
        this.dep_id = dep_id;
        this.dep_title = dep_title;
        this.dep_imageId = dep_imageId;
    }

    public Department() {
    }

    public Department(String dep_content) {
        this.dep_content = dep_content;
    }

    public Department(int dep_id, String dep_title, String dep_content, String dep_imageId) {
        this.dep_id = dep_id;
        this.dep_title = dep_title;
        this.dep_content = dep_content;
        this.dep_imageId = dep_imageId;
    }

    public Department(int dep_id, String dep_title, String dep_content, String dep_imageId, String dep_address, String dep_contact, String dep_email) {
        this.dep_id = dep_id;
        this.dep_title = dep_title;
        this.dep_content = dep_content;
        this.dep_imageId = dep_imageId;
        this.dep_address = dep_address;
        this.dep_contact = dep_contact;
        this.dep_email = dep_email;
    }
}
