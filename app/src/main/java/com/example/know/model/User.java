package com.example.know.model;

import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by know on 2016/3/10.
 */
public class User implements Serializable{

    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("userId")
    private int userId;
    @Column("name")
    private String name;
    @Column("password")
    private String password;
    @Column("flower")
    private int flower;
    @Column("headUrl")
    @SerializedName("av")
    private String headUrl;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFlower() {
        return flower;
    }

    public void setFlower(int flower) {
        this.flower = flower;
    }

    public String getHeadUrl() {
        //return "http://sinacloud.net/artist/avatar/a"+userId+"_"+headUrl+".png";
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", flower=" + flower +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }
}
