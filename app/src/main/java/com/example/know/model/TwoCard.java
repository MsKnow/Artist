package com.example.know.model;

import java.util.List;

/**
 * Created by know on 2016/2/21.
 */
public class TwoCard {

    public SelfieCard selfie;
    List<ArtCard> arts;
    //ArtCard artCard;

    @Override
    public String toString() {
        return selfie.toString()+" "+arts.size();
    }
}
