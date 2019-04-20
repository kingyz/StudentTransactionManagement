package com.kingyzll.bean;

public class Teacher {
    private int t_id;
    private String t_name;
    private String t_type;
    private String t_email;
    private String t_portrait;

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_type() {
        return t_type;
    }

    public void setT_type(String t_type) {
        this.t_type = t_type;
    }

    public String getT_email() {
        return t_email;
    }

    public void setT_email(String t_email) {
        this.t_email = t_email;
    }

    public String getT_portrait() {
        return t_portrait;
    }

    public void setT_portrait(String t_portrait) {
        this.t_portrait = t_portrait;
    }


    public Teacher(int t_id, String t_name, String t_type, String t_email, String t_portrait) {
        super();
        this.t_id = t_id;
        this.t_name = t_name;
        this.t_type = t_type;
        this.t_email = t_email;
        this.t_portrait = t_portrait;

    }

}