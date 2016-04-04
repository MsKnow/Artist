package com.example.know.presenter.impl;

import android.util.Log;

import com.example.know.artist.view.CardinView;
import com.example.know.model.ArtCard;
import com.example.know.presenter.CardinPresenter;
import com.example.know.retrofit.ArtService;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by know on 2016/4/4.
 */
public class CardinPresenterImpl implements CardinPresenter{

    CardinView cardinView;
    ArtService artService;

    public CardinPresenterImpl(CardinView cardinView, ArtService artService) {
        this.cardinView = cardinView;
        this.artService = artService;
    }

    @Override
    public void getArts(int selfId) {
        artService.getArts(233,selfId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ArtCard>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("oncomplete", "ff");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onerror",e.toString());
                    }

                    @Override
                    public void onNext(List<ArtCard> artCards) {
                        Log.e("atrs"," "+artCards.size()+artCards.get(0).getImUrl());
                        cardinView.refreshArts(artCards);
                    }
                });
    }
}
