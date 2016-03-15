package com.example.know.model;

import java.util.Date;

/**
 * Created by know on 2016/2/21.
 */
public class Card {
    private int id;
    private int userId;
    private String imUrl;
    private Date uploadTime;
    private boolean visible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImUrl() {
        return imUrl;
    }

    public void setImUrl(String imUrl) {
        this.imUrl = imUrl;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", userId=" + userId +
                ", imUrl='" + imUrl + '\'' +
                ", uploadTime=" + uploadTime +
                ", visible=" + visible +
                '}';
    }
}
