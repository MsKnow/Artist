package com.example.know.artist;

import android.app.Application;
import android.content.Context;

import com.example.know.util.ToastUtil;
import com.litesuits.orm.LiteOrm;

/**
 * Created by know on 2016/2/19.
 */
public class App extends Application {

    public static Context mContext;
    public static LiteOrm mDb;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        ToastUtil.register(this);

        mDb = LiteOrm.newSingleInstance(mContext,"art.db");
        mDb.setDebugged(false);

    }
}
