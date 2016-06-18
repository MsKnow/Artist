package com.example.know.artist.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.know.artist.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by know on 2016/4/7.
 */
public abstract class RefreshActivity extends ToolbarActivity {

    @Bind(R.id.swipe)protected SwipeRefreshLayout swipeRL;

    abstract protected void refresh();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        swipeRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

    }

    public void setRefresh(boolean refresh) {
        if (swipeRL == null) {
            return;
        }
        if (!refresh) {
            //mIsRequestDataRefresh = false;
            // 防止刷新消失太快，让子弹飞一会儿.
            swipeRL.postDelayed(new Runnable() {
                @Override public void run() {
                    if (swipeRL != null) {
                        swipeRL.setRefreshing(false);
                    }
                }
            }, 1000);
        } else {
            swipeRL.setRefreshing(true);
        }
    }

}
