package com.example.know.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by know on 2016/2/21.
 */
public class TwoCard implements Serializable{



    public String userName;
    public String av;
    public SelfieCard selfie;
    public ArtCard art;
    //ArtCard artCard;
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
    public void setSelfie(SelfieCard selfie) {
        this.selfie = selfie;
    }

    @Override
    public String toString() {
        String str = selfie.toString();
        if (art!=null){
            str += "-----" + art.toString();
        }
        return str;
    }
}
