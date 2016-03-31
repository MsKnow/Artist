package com.example.know.model;

import java.util.List;

/**
 * Created by know on 2016/2/21.
 */
public class TwoCard {

    public SelfieCard selfie;
    public List<ArtCard> arts;
    //ArtCard artCard;

    public void setSelfie(SelfieCard selfie) {
        this.selfie = selfie;
    }

    @Override
    public String toString() {
        if(arts != null){
            String str = "";
            for (int i=0;i<arts.size();i++){
                str += "\n"+arts.get(i).toString();
            }
            return selfie.toString()+" "+str;
        }else{
            return selfie.toString()+" art is null";
        }

    }
}
