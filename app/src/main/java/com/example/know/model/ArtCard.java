package com.example.know.model;

/**
 * Created by know on 2016/2/21.
 */
public class ArtCard extends Card {

    public String userName;
    public String av;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAv() {
        return av;
    }

    public void setAv(String av) {
        this.av = av;
    }

    private int selfId;
    private int flower;


    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }



    public int getFlower() {
        return flower;
    }

    public void setFlower(int flower) {
        this.flower = flower;
    }

    @Override
    public String toString() {

        return super.toString()+"ArtCard{" +
                " flower=" + flower +
                " selfId" + selfId +
                '}';
    }
}
