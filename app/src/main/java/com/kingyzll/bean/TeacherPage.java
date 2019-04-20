package com.kingyzll.bean;

public class TeacherPage {
    private String courese;
    private String classTime;

    public String getCourese() {
        return courese;
    }

    public void setCourese(String courese) {
        this.courese = courese;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public TeacherPage(String courese, String classTime) {
        super();
        this.courese = courese;
        this.classTime = classTime;
    }

}