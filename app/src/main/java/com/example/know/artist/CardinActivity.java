package com.example.know.artist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.know.adapter.ArtsAdapter;
import com.example.know.adapter.OnArtClickListener;
import com.example.know.artist.base.RefreshActivity;
import com.example.know.artist.view.CardinView;
import com.example.know.model.ArtCard;
import com.example.know.presenter.impl.CardinPresenterImpl;
import com.example.know.retrofit.ServiceFactory;
import com.example.know.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CardinActivity extends RefreshActivity implements CardinView{

    private CardinPresenterImpl presenter;

    int selfId,userId;

    private List<ArtCard> arts;
    private ArtsAdapter artsAdapter;
    @Bind(R.id.list_art)RecyclerView artList;

    @Override
    protected int getContentId() {
        return R.layout.activity_cardin;
    }



    @Override
    protected void refresh() {
        presenter.getArts(selfId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        presenter = new CardinPresenterImpl(this,artService);

        initList();

        Intent intentF = getIntent();
        selfId = intentF.getIntExtra("selfId",-1);

        userId = intentF.getIntExtra("userId",-1);

        presenter.getArts(selfId);


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
            Intent intent = new Intent(CardinActivity.this,UploadActivity.class);
            intent.putExtra("selfId", selfId);
            intent.putExtra("userId", userId);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        swipeRL.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRL.setRefreshing(false);
    }

    @Override
    public void refreshArts(List<ArtCard> artss) {
        arts.clear();
        arts.addAll(artss);
        artsAdapter.notifyDataSetChanged();
        Log.e("refresh", "" + arts.get(0).getUserId());
    }

    @Override
    public void changeLove(int artId, int flower) {

    }

    private void initList(){
        arts = new ArrayList<>();
        artsAdapter = new ArtsAdapter(arts,this);

        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        artList.setLayoutManager(layoutManager);
        artsAdapter.setOnClickListener(getOnArtClickListener());
        artList.setAdapter(artsAdapter);
    }

    private OnArtClickListener getOnArtClickListener(){
        return (v,art,loveImg, artCard) -> {

            if (userId == -10){
                ToastUtil.tShort("请先登录");
            }else if(userId == -1){
                if (MainActivity.me!=null){
                    love(MainActivity.me.getUserId(),artCard.getId(),artCard.getUserId());
                }else
                    ToastUtil.tShort("请先登录..");
            }else {
                love(userId,artCard.getId(),artCard.getUserId());
            }

        };
    }

    private void love(int userId,int artId,int artistId){

        ServiceFactory.getService().love(userId,artId,artistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.e("love", "-------" + result.toString());
                    if (result.getResultCode()>0){
                        ToastUtil.tShort(result.getResultDes());

                        presenter.getArts(selfId);

                    }else if (result.getResultCode()==-2){
                        ToastUtil.tShort("+1s");
                    }
                },throwable -> {
                    throwable.printStackTrace();
                    ToastUtil.tShort("网络错误，请重试");
                });
    }

    @Override
    protected void onResume() {

        if(presenter == null){
            presenter = new CardinPresenterImpl(this,artService);
        }
        presenter.getArts(selfId);

        super.onResume();
    }
}
