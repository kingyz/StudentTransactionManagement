package com.kingyzll.bean;

public class Program {
    private int p_id;
    private String p_title;
    private String p_feature;
    private String p_intro;
    private String p_core;
    private String p_imgurl;

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getP_title() {
        return p_title;
    }

    public void setP_title(String p_title) {
        this.p_title = p_title;
    }

    public String getP_feature() {
        return p_feature;
    }

    public void setP_feature(String p_feature) {
        this.p_feature = p_feature;
    }

    public String getP_intro() {
        return p_intro;
    }

    public void setP_intro(String p_intro) {
        this.p_intro = p_intro;
    }

    public String getP_core() {
        return p_core;
    }

    public void setP_core(String p_core) {
        this.p_core = p_core;
    }

    public String getP_imgurl() {
        return p_imgurl;
    }

    public void setP_imgurl(String p_imgurl) {
        this.p_imgurl = p_imgurl;
    }

    public Program(int p_id, String p_title, String p_feature, String p_intro, String p_core, String p_imgurl) {
        super();
        this.p_id = p_id;
        this.p_title = p_title;
        this.p_feature = p_feature;
        this.p_intro = p_intro;
        this.p_core = p_core;
        this.p_imgurl = p_imgurl;
    }

}
