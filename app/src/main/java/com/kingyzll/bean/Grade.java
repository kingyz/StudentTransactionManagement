package com.kingyzll.bean;

public class Grade {
    private String courseName;
    private String property;
    private String credit;
    private String score;

    public Grade(String courseName, String property, String credit, String score) {
        this.courseName = courseName;
        this.property = property;
        this.credit = credit;
        this.score = score;
    }

    public Grade() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
