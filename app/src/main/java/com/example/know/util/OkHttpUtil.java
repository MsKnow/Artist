package com.example.know.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by know on 2016/2/20.
 */
public class OkHttpUtil {

    //private static OkHttpClient clientt = new OkHttpClient();
    public static final OkHttpClient client = new OkHttpClient.Builder()  //clientt.newBuilder()
            //.readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS).build();
    static {

    }

    /**
     * 不会开启异步线程，所以一般在子线程调用？
     * @param request 传入request
     * @return 返回response
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException{
        return client.newCall(request).execute();
    }

    /**
     * 开启异步线程 调用callback方法时阻塞该线程嗯。
     * @param request
     * @param callback
     */
    public static void enqueue(Request request, Callback callback){
        client.newCall(request).enqueue(callback);
    }

    public static void getStr(String url,Callback callback){
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);

    }

    //// TODO: 2016/2/20 加入参数为String的回调函数

    public static String getString(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()){
            return response.body().string();
        }else {
            throw new IOException("Unexpected code "+ response);
        }
    }


}
