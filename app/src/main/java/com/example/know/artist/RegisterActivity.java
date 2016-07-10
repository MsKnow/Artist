package com.example.know.artist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.EditText;

import com.example.know.retrofit.ServiceFactory;
import com.example.know.util.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.btn_signup)AppCompatButton registerButton;
    @Bind(R.id.input_name)EditText nameEdit;
    @Bind(R.id.input_password)EditText passwordEdit;
    @Bind(R.id.input_password_confirm)EditText confirmEdit;

    public static final String USERNAME = "USERNAME";

    String name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_signup)
    public void PreRegister(){

        name = nameEdit.getText().toString();
        password = passwordEdit.getText().toString();
        String confirm = confirmEdit.getText().toString();
        if ("".equals(name)||"".equals(password)||"".equals(confirm)){
            ToastUtil.tShort("不能空着");
        }else if (name.length()>10||password.length()>20){
            ToastUtil.tShort("太长啦");
        }else if (!(password.equals(confirm))){
            ToastUtil.tShort("两次密码不一样");
        }else {
            register(name,password);
        }

    }

    public void register(String name,String password){

        registerButton.setEnabled(false);
        registerButton.setText("注册中。。");

        ServiceFactory.getService()
                .Register(name, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                    registerButton.setEnabled(true);
                    registerButton.setText("注册");

                    if (result.getResultCode()==1){
                        Intent intent = new Intent();
                        intent.putExtra(USERNAME, name);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    ToastUtil.tShort(result.getResultDes());
                    Log.e("result",result.toString());
                }, throwable -> {

                    registerButton.setEnabled(true);
                    registerButton.setText("注册");

                    throwable.printStackTrace();
            });
    }

}
