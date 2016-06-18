package com.example.know.util;

import android.content.Context;
import android.widget.Toast;

import com.example.know.artist.App;

/**
 * Created by know on 2016/4/14.
 */
public class ToastUtil {

    public static Context mContext;

    public static void register(Context context) {
        mContext = context.getApplicationContext();
    }

    private ToastUtil() {}

    private static void check() {
        if (App.mContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastUtils.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }

    public static void tShort(CharSequence str){
        check();
        Toast.makeText(App.mContext,str,Toast.LENGTH_SHORT).show();
    }



}
