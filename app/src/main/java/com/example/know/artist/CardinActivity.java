package com.example.know.artist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class CardinActivity extends ToolbarActivity {

    @Override
    protected int getContentId() {
        return R.layout.activity_cardin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intentF = getIntent();
        int selfId = intentF.getIntExtra("selfId",0);
        /*Intent intent = new Intent(CardinActivity.this,UploadActivity.class);
        intent.putExtra("selfId", id);
        startActivity(intent);*/
    }
}
