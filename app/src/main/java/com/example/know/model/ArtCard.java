package com.example.know.model;

/**
 * Created by know on 2016/2/21.
 */
public class ArtCard extends Card {

    private int flower;

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    private int selfId;

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
