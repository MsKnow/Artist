package com.example.know.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by know on 2016/3/4.
 */
public class ServiceFactory {

    private static ArtService service = null;

    public static  ArtService getService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://1.selfie.applinzi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ArtService.class);
        return service;
    }

}
