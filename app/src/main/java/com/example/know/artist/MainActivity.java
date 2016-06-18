package com.example.know.artist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.know.adapter.OnCardClickListener;
import com.example.know.adapter.TwoCardAdapter;
import com.example.know.artist.base.RefreshActivity;
import com.example.know.artist.base.ToolbarActivity;
import com.example.know.model.TwoCard;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends RefreshActivity {

    //@Bind(R.id.im_post)ImageView imagePost;
    @Bind(R.id.list_card)RecyclerView cardList;
    @Bind(R.id.fab)FloatingActionButton fab;
    //String accessKey = "zb7ci73kAL8ZNoy5Yd9Q";
    //String secretKey = "f618bb0da5d82c7dd43502c0f9347f1383cfd235";
    List<TwoCard> twoCards;
    TwoCardAdapter adapter;
    @Bind(R.id.drawerLayout)DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }
    @Override
    protected void refresh() {
        getcards();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        intiList();
        initEvent();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                intent.putExtra("selfId",-1);//just selfie
                startActivity(intent);

            }
        });

        Log.e("oncreate", "oncreate");

        //getcards();
    }

    private void intiList(){

        /*SelfieCard selfieCard = new SelfieCard();
        selfieCard.setImUrl("http://sinacloud.net/artist/selfie/s2.png");
        TwoCard twoCard = new TwoCard();
        twoCard.setSelfie(selfieCard);*/
        twoCards = new ArrayList<>();
        adapter = new TwoCardAdapter(twoCards, MainActivity.this);
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this, 1, false);
        cardList.setLayoutManager(llm);
        adapter.setOClickListener(getOnCardClickListener());
        cardList.setAdapter(adapter);
    }
    private void initEvent() {
        if(getSupportActionBar()!=null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.abc_action_bar_home_description,
                R.string.abc_action_bar_home_description_format);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

    }

    @OnClick(R.id.im_avatar)
    public void toLogin(){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);

        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    protected void onResume() {
        //Glide.with(this).load("http://imgx.sinacloud.net/artist/r_max,e_oil_paint/selfie/1.jpg").into(imagePost);
        getcards();

        super.onResume();
    }

    private void getcards(){

        artService.getTwoCard(233)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<List<TwoCard>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("getwo", "onCompleted");
                        setRefresh(false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("getwo", "onError");
                        setRefresh(false);
                    }

                    @Override
                    public void onNext(List<TwoCard> twoCs) {
                        twoCards.clear();
                        twoCards.addAll(twoCs);
                        //cardList.setAdapter(adapter);
                        //adapter.notifyItemInserted(1);
                        adapter.notifyDataSetChanged();

                        for(int i = 0;i<twoCards.size();i++){
                            Log.e("getwo", twoCards.get(i).toString());
                        }

                    }
                });

    }

    private OnCardClickListener getOnCardClickListener(){
        return (v,selfCard,artCard,twoCard)->{
            if(twoCard == null) return;
            int id = twoCard.selfie.getId();
            if(v == selfCard){
                Log.e("selfClick"," id: "+id);
            }
            if(v == artCard){
                Log.e("artClick", " id: " + id);
            }
            Intent intent = new Intent(MainActivity.this,CardinActivity.class);
            //intent.putExtra("selfId",(Serializable)twoCard.arts); 使用数据库做第一次更新
            intent.putExtra("selfId",id);
            startActivity(intent);
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
