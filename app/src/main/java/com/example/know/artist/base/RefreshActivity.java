package com.example.know.artist.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by know on 2016/4/7.
 */
public abstract class RefreshActivity extends ToolbarActivity {

    SwipeRefreshLayout swipeRL;

    abstract protected int getSwipeId();

    abstract protected void refresh();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        swipeRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }
}
