package com.example.know.artist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.know.adapter.ArtPagerAdapter;
import com.example.know.adapter.OnArtClickListener;
import com.example.know.adapter.ShadowTransformer;
import com.example.know.artist.base.ToolbarActivity;
import com.example.know.artist.view.CardinView;
import com.example.know.model.ArtCard;
import com.example.know.model.TwoCard;
import com.example.know.presenter.impl.CardinPresenterImpl;
import com.example.know.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArtsActivity extends ToolbarActivity implements CardinView{

    @Bind(R.id.vp_art)ViewPager artPager;
    @Bind(R.id.im_selfie_art) ImageView selfieImage;

    private ArtPagerAdapter artPagerAdapter;
    private ShadowTransformer pagerTransformer;

    private int selfId,userId;
    private TwoCard twoCard;
    List<ArtCard> artCards;

    CardinPresenterImpl artsPresenter;


    @Override
    protected int getContentId() {
        return R.layout.activity_arts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        artCards = new ArrayList<>();

        selfId = getIntent().getIntExtra("selfId",-1);
        userId = getIntent().getIntExtra("userId",-1);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        twoCard = (TwoCard) bundle.getSerializable("twoCard");
        //twoCard = (TwoCard) getIntent().getSerializableExtra("twoCard");

        artsPresenter = new CardinPresenterImpl(this,artService);

        Log.e("jj",twoCard.selfie.getImUrl());

        initPager();

    }

    private void initPager(){

        Glide.with(this)
                .load(twoCard
                        .selfie
                        .getImUrl())
                .into(selfieImage);

        if (twoCard.art != null) {
            artCards.add(twoCard.art);
        }else {
            artCards.add(ArtCard.getDefault());
        }


        artPagerAdapter = new ArtPagerAdapter(this,artCards);

        pagerTransformer = new ShadowTransformer(artPager,artPagerAdapter);
        pagerTransformer.enableScaling(true);

        artPagerAdapter.setOnArtClickListener(getOnArtClickListener());
        artPager.setAdapter(artPagerAdapter);
        artPager.setPageTransformer(false,pagerTransformer);


    }

    private OnArtClickListener getOnArtClickListener(){
        return (v,artImg,loveImg, artCard) -> {

            Log.e("artClick",artCard.getUserName());

            if (v == loveImg){
                Log.e("artClick",artCard.getUserId()
                        +"loveImg--->"+userId);
                if (userId == -10){
                    ToastUtil.tShort("请先登录");
                }else if(userId == -1){
                    if (MainActivity.me!=null){
                        if (artCard.getLovely()<1){
                            artsPresenter.love(MainActivity.me.getUserId(),artCard.getId(),artCard.getUserId());
                        }else {
                            ToastUtil.tShort("不可爱");
                        }
                        //artService.love(MainActivity.me.getUserId(),artCard.getId(),artCard.getUserId());

                    }else
                        ToastUtil.tShort("请先登录..");
                }else {
                    //artService.love(userId,artCard.getId(),artCard.getUserId());
                    artsPresenter.love(userId,artCard.getId(),artCard.getUserId());
                }
            }else if (v == artImg){
                ToastUtil.tShort("展开大图");
            }

        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cardin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (userId == -10){
            ToastUtil.tShort("请先登录");

        }else {
            Intent intent = new Intent(this,UploadActivity.class);
            intent.putExtra("selfId", selfId);
            intent.putExtra("userId", userId);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void changeLove(int artId,int flower){
        for (ArtCard artCard: artCards) {
            if (artCard.getId() == artId){
                artCard.setFlower(artCard.getFlower()+flower);
            }
        }
        artPagerAdapter.setArtCards(artCards);
    }

    @Override
    public void refreshArts(List<ArtCard> artCardss) {

        if (artCardss == null || artCardss.size() == 0){
            ToastUtil.tShort("很难描绘");
            return;
        }
        artCards = artCardss;
        //Log.e("atrs"," "+artCards.size()+artCards.get(0).getImUrl());

        artPagerAdapter.setArtCards(artCards);

    }


    @Override
    protected void onResume() {
        super.onResume();
        artsPresenter.getArts(selfId);
    }
}
