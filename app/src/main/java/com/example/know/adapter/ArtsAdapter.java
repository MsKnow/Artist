package com.example.know.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.know.artist.R;
import com.example.know.model.ArtCard;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by know on 2016/4/4.
 */
public class ArtsAdapter extends RecyclerView.Adapter<ArtsAdapter.ViewHolder>{

    List<ArtCard> arts;
    Context context;

    public ArtsAdapter(List<ArtCard> arts, Context context) {
        this.arts = arts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_artcard,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArtCard art = arts.get(position);
        holder.tv_un.setText(art.getUserId()+"");
        Glide.with(context)
                .load(art.getImUrl())
                .into(holder.im_art);
    }

    @Override
    public int getItemCount() {
        return arts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.im_art_art)ImageView im_art;
        @Bind(R.id.tv_usern)TextView tv_un;

        View artView;
        public ViewHolder(View itemView) {
            super(itemView);
            artView = itemView;
            ButterKnife.bind(this,artView);


        }
    }

}
