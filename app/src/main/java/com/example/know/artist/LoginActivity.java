package com.example.know.artist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.input_name)EditText nameEdit;
    @Bind(R.id.input_password)EditText passwordEdit;

    @Bind(R.id.link_signup)TextView registerText;
    public static final int REQUSET = 1;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);



    }

    @OnClick(R.id.link_signup)
    public void toRegister(){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivityForResult(intent,REQUSET);
    }

        private void login(){

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
