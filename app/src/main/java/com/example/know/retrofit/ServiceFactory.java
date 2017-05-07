package com.example.know.retrofit;

import com.example.know.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by know on 2016/3/4.
 */
public class ServiceFactory {

    private static ArtService service = null;

    public static  ArtService getService(){

        if(service != null)return service;

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://1.artist.applinzi.com/")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(OkHttpUtil.client)
                .build();
        service = retrofit.create(ArtService.class);
        return service;
    }

}
