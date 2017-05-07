package com.example.know.model;

import java.util.Date;

/**
 * Created by know on 2016/2/21.
 */
public class ArtCard extends Card {

    private String userName;
    private String av;
    private int selfId;
    private int flower;
    private int lovely = 0;

    public int getLovely() {
        return lovely;
    }

    public void setLovely(int lovely) {
        this.lovely = lovely;
    }

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

    private static ArtCard defaultArtCard;

    public static ArtCard getDefault(){
        if (defaultArtCard == null){
            defaultArtCard = new ArtCard();
            defaultArtCard.setImUrl("");
            defaultArtCard.setUploadTime(new Date());
            defaultArtCard.setUserId(0);
            defaultArtCard.setVisible(true);
            defaultArtCard.setId(0);
            defaultArtCard.setAv("");
            defaultArtCard.setFlower(233);
            defaultArtCard.setSelfId(0);
            defaultArtCard.setUserName("赵四");
            defaultArtCard.setLovely(0);
        }

        return defaultArtCard;

    }

}
