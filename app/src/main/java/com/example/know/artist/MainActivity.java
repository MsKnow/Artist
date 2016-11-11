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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.know.adapter.OnCardClickListener;
import com.example.know.adapter.TwoCardAdapter;
import com.example.know.artist.base.RefreshActivity;
import com.example.know.artist.base.ToolbarActivity;
import com.example.know.model.TwoCard;
import com.example.know.model.User;
import com.example.know.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends RefreshActivity {

    public static final int REQUSETME = 1;
    //@Bind(R.id.im_post)ImageView imagePost;
    @Bind(R.id.list_card)RecyclerView cardList;
    @Bind(R.id.fab)FloatingActionButton fab;
    //String accessKey = "zb7ci73kAL8ZNoy5Yd9Q";
    //String secretKey = "f618bb0da5d82c7dd43502c0f9347f1383cfd235";
    List<TwoCard> twoCards;
    TwoCardAdapter adapter;
    //@Bind(R.id.drawer)View Drawer;
    @Bind(R.id.drawerLayout)DrawerLayout drawerLayout;
    @Bind(R.id.im_avatar)ImageView avatar;
    @Bind(R.id.tv_user_name)TextView myNameText;
    @Bind(R.id.tv_user_flower)TextView myFlowers;
    ActionBarDrawerToggle drawerToggle;
    boolean online = false;
    User me;

    public void checkLocal(){

    }
    private void checkOnline(){

    }

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

        List<User> users = App.mDb.query(User.class);
        if (users.size()>0){
            me = users.get(0);
            online = true;
            refreshMe(me);
        }

        intiList();
        initEvent();


        fab.setOnClickListener(view -> {
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/
            if (me==null){
                ToastUtil.tShort("请先登录");
            }else{
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                intent.putExtra("selfId", -1);//just selfie
                intent.putExtra("userId", me.getUserId());
                startActivity(intent);

            }

        });

        Log.e("oncreate", "oncreate");

        //getcards();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUSETME&&resultCode==RESULT_OK){
            User mee = (User) data.getSerializableExtra("me");
            Log.e("requestME",mee.toString());

            me = mee;
            App.mDb.save(mee);
            online = true;

            refreshMe(mee);
        }
    }
    private void refreshMe(User me){
        myNameText.setText(me.getName());
        Glide.with(MainActivity.this).load(me.getHeadUrl()).into(avatar);
        Log.e("ava---->",me.getHeadUrl());
        myFlowers.setText("" + me.getFlower());
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
        //drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.addDrawerListener(drawerToggle);

    }
    @OnClick(R.id.tv_user_name)
    public void login(){
        toLogin();
    }


    @OnClick(R.id.bt_quit)
    public void logout(){
        myNameText.setText("未登录");
        avatar.setImageResource(R.mipmap.artist);
        App.mDb.delete(User.class);
        online = false;

    }
    @OnClick(R.id.im_avatar)
    public void toLogin(){

        if (online){

            Intent intent = new Intent(MainActivity.this, UploadActivity.class);
            intent.putExtra("selfId", 0);//just avatar
            intent.putExtra("userId",me.getUserId());
            startActivityForResult(intent, REQUSETME);

        }else{
            //drawerLayout.closeDrawer(Gravity.LEFT);
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(intent, REQUSETME);
        }

    }

    @OnClick(R.id.drawer)
    public void drawerTest(){
        Log.e("drawer","drawerOnclick");
    }

    @Override
    protected void onResume() {
        //Glide.with(this).load("http://imgx.sinacloud.net/artist/r_max,e_oil_paint/selfie/1.jpg").into(imagePost);
        getcards();

        super.onResume();
    }

    private void getcards(){


        /*artService.getTwoCardT(1)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<ResponseBody>() {
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
                    public void onNext(ResponseBody responseBody) {

                        String body = "44" ;
                        try {
                            body = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.e("------->",body);

                    }
                });*/

        int id = 0;
        if (me == null){
            id = -1;
        }else {
            id = me.getUserId();
        }

        /*artService.getTwoCardT(id)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(responseBody -> {
                    try {
                        String body = responseBody.string();
                        Log.e("twoCT","----->"+body);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });*/

        artService.getTwoCard(id)
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
            intent.putExtra("userId",me.getUserId());
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
