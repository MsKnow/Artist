package com.example.know.artist.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.know.artist.R;
import com.example.know.retrofit.ArtService;
import com.example.know.retrofit.ServiceFactory;

/**
 * Created by know on 2016/3/6.
 */
public abstract class ToolbarActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    public static final ArtService artService = ServiceFactory.getService();

    abstract protected int getContentId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

    }
}
