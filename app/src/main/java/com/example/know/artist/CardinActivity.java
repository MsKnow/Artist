package com.example.know.artist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.know.adapter.ArtsAdapter;
import com.example.know.artist.base.RefreshActivity;
import com.example.know.artist.base.ToolbarActivity;
import com.example.know.artist.view.CardinView;
import com.example.know.model.ArtCard;
import com.example.know.presenter.impl.CardinPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CardinActivity extends RefreshActivity implements CardinView{

    private CardinPresenterImpl presenter;

    int selfId;

    private List<ArtCard> arts;
    private ArtsAdapter artsAdapter;
    @Bind(R.id.list_art)RecyclerView artList;

    @Override
    protected int getContentId() {
        return R.layout.activity_cardin;
    }

    private void initList(){
        arts = new ArrayList<>();
        artsAdapter = new ArtsAdapter(arts,this);

        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        artList.setLayoutManager(layoutManager);

        artList.setAdapter(artsAdapter);
    }

    @Override
    protected void refresh() {
        presenter.getArts(selfId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        presenter = new CardinPresenterImpl(this,artService);

        initList();

        Intent intentF = getIntent();
        selfId = intentF.getIntExtra("selfId",0);
        presenter.getArts(selfId);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cardin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(CardinActivity.this,UploadActivity.class);
        intent.putExtra("selfId", selfId);
        startActivity(intent);

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
        Log.e("refresh",""+arts.get(0).getUserId());
    }
}
