package com.kingyzll.bean;

public class DownloadedFiles {

    private String title;
    private String type;
    private int imageId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    public DownloadedFiles(String title, String type, int imageId) {
        this.title = title;
        this.type = type;
        this.imageId = imageId;
    }
}
