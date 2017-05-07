package com.example.know.presenter.impl;

import android.util.Log;

import com.example.know.artist.view.CardinView;
import com.example.know.model.ArtCard;
import com.example.know.presenter.CardinPresenter;
import com.example.know.retrofit.ArtService;
import com.example.know.retrofit.Result;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by know on 2016/4/4.
 */
public class CardinPresenterImpl implements CardinPresenter{

    private CardinView cardinView;
    private ArtService artService;

    public CardinPresenterImpl(CardinView cardinView, ArtService artService) {
        this.cardinView = cardinView;
        this.artService = artService;
    }

    @Override
    public void getArts(int selfId) {
        cardinView.showLoading();

        /*artService.getArtsT(233,selfId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.e("oncomplete", "ff");
                        cardinView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onerror",e.toString());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            Log.e("atrs----->",body);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });*/


        artService.getArts(selfId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ArtCard>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("oncomplete", "ff");
                        cardinView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onerror",e.toString());
                    }

                    @Override
                    public void onNext(List<ArtCard> artCards) {
                        for (ArtCard artcard : artCards) {
                            Log.e("atrs---"," "+artCards.size()+artcard.getImUrl());
                        }

                        cardinView.refreshArts(artCards);
                    }
                });
    }

    @Override
    public void love(int userId, int artId, int artistId) {


        artService.love(userId,artId,artistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.e("love","result----->"+result.toString());
                    if (result.getResultCode()>0){
                        Log.e("love","+1--->"+artId);
                        cardinView.changeLove(artId,1);
                    }else {
                        Log.e("love","+0--->"+artId);
                        //cardinView.changeLove(artId,-1);
                    }

                }, throwable -> {
                    Log.e("love","error--->"+artId);
                    throwable.printStackTrace();
                });
    }
}
