package com.example.know.retrofit;

import com.example.know.model.User;

/**
 * Created by yang on 2016/6/18.
 */
public class Result {

    int resultCode;
    String resultDes;
    String error;
    User user;

    public User getUser() {
        return user;
    }
    public String getError() {
        return error;
    }
    public int getResultCode() {
        return resultCode;
    }
    public String getResultDes() {
        return resultDes;
    }



    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + resultCode +
                ", resultDes='" + resultDes + '\'' +
                ", error='" + error + '\'' +
                '}'+"user--->";//+getUser().toString();
    }
}
