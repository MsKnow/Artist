package com.example.know.artist.view;

import com.example.know.model.ArtCard;

import java.util.List;

/**
 * Created by know on 2016/4/4.
 */
public interface CardinView {

    void showLoading();

    void hideLoading();

    void refreshArts(List<ArtCard> arts);

}
