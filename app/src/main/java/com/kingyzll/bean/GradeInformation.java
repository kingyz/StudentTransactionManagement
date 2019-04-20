package com.kingyzll.bean;

public class GradeInformation {
    private String course;
    private String property;
    private String credit;
    private String semester;
    private String code;
    private String score;
    private String gpa;
    private String supplymentary;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getSupplymentary() {
        return supplymentary;
    }

    public void setSupplymentary(String supplymentary) {
        this.supplymentary = supplymentary;
    }

    public GradeInformation(String course, String property, String credit, String semester, String score,
                            String gpa, String supplymentary, String code) {
        super();
        this.course = course;
        this.property = property;
        this.credit = credit;
        this.semester = semester;
        this.code = code;
        this.score = score;
        this.gpa = gpa;
        this.supplymentary = supplymentary;
    }


}
