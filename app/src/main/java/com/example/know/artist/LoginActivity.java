package com.example.know.artist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.know.retrofit.Result;
import com.example.know.retrofit.ServiceFactory;
import com.example.know.util.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.input_name)EditText nameEdit;
    @Bind(R.id.input_password)EditText passwordEdit;
    @Bind(R.id.btn_login)Button loginButton;

    @Bind(R.id.link_signup)TextView registerText;
    public static final int REQUSET = 1;

    String name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.link_signup)
    public void toRegister(){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivityForResult(intent, REQUSET);
    }


    @OnClick(R.id.btn_login)
    public void preLogin(){

        name = nameEdit.getText().toString();
        password = passwordEdit.getText().toString();
        if ("".equals(name)||"".equals(password)){
            ToastUtil.tShort("不能空着");
        }else if (name.length()>10||password.length()>20){
            ToastUtil.tShort("太长啦");
        }else{
            login(name,password);
        }

    }
    private void login(String name,String password){

        loginButton.setEnabled(false);
        loginButton.setText("登录中。。。");


        ServiceFactory.getService().login(name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.e("login",result.toString()+result.getUser().toString());
                    if(result.getResultCode()>0){
                        Intent intent = new Intent();
                        intent.putExtra("me",result.getUser());
                        setResult(RESULT_OK, intent);

                        finish();
                    }

                    ToastUtil.tShort(result.getResultDes());
                    loginButton.setEnabled(true);
                    loginButton.setText("登录");

                },throwable -> {
                    throwable.printStackTrace();
                    ToastUtil.tShort("网络错误啦");
                    loginButton.setEnabled(true);
                    loginButton.setText("登录");
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.REQUSET && resultCode == RESULT_OK) {
            name = data.getStringExtra(RegisterActivity.USERNAME);

            nameEdit.setText(name);

        }
    }

}
