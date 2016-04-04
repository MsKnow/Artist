package com.example.know.artist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.know.adapter.ArtsAdapter;
import com.example.know.artist.view.CardinView;
import com.example.know.model.ArtCard;
import com.example.know.presenter.impl.CardinPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CardinActivity extends ToolbarActivity implements CardinView{

    private CardinPresenterImpl presenter;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        presenter = new CardinPresenterImpl(this,artService);

        initList();

        Intent intentF = getIntent();
        int selfId = intentF.getIntExtra("selfId",0);
        presenter.getArts(selfId);

        /*Intent intent = new Intent(CardinActivity.this,UploadActivity.class);
        intent.putExtra("selfId", id);
        startActivity(intent);*/
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void refreshArts(List<ArtCard> artss) {
        arts.clear();
        arts.addAll(artss);
        artsAdapter.notifyDataSetChanged();
        Log.e("refresh",""+arts.get(0).getUserId());
    }
}
