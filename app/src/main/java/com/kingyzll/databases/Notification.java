package com.kingyzll.databases;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class Notification extends DataSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String content;
    private String date;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
