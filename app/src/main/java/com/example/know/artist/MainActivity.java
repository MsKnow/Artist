package com.example.know.artist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.know.adapter.OnCardClickListener;
import com.example.know.adapter.TwoCardAdapter;
import com.example.know.model.TwoCard;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


import okhttp3.MediaType;
import okhttp3.RequestBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends ToolbarActivity {

    //@Bind(R.id.im_post)ImageView imagePost;
    @Bind(R.id.list_card)RecyclerView cardList;
    //String accessKey = "zb7ci73kAL8ZNoy5Yd9Q";
    //String secretKey = "f618bb0da5d82c7dd43502c0f9347f1383cfd235";

    TwoCardAdapter adapter = null;

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adapter = new TwoCardAdapter(null,this);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);

            }
        });

        Log.e("oncreate", "oncreate");

        //getcards();
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("getwo", "onError");
                    }

                    @Override
                    public void onNext(List<TwoCard> twoCards) {

                        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this, 1, false);
                        cardList.setLayoutManager(llm);

                        adapter = new TwoCardAdapter(twoCards, MainActivity.this);

                        adapter.setOClickListener(getOnCardClickListener());

                        cardList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        adapter.setOClickListener(getOnCardClickListener());

                        Log.e("getwo", twoCards.get(1).toString());

                    }
                });

    }

    private OnCardClickListener getOnCardClickListener(){
        return (v,selfCard,artCard,twoCard)->{
            if(twoCard == null) return;
            if(v == selfCard){
                Log.e("selfClick"," id: "+twoCard.selfie.getId());
            }
            if(v == artCard){
                Log.e("artClick"," id: "+twoCard.selfie.getId());
            }
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
