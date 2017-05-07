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
import com.example.know.util.DateUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by know on 2016/4/4.
 */
public class ArtsAdapter extends RecyclerView.Adapter<ArtsAdapter.ViewHolder>{

    List<ArtCard> arts;
    Context context;
    OnArtClickListener listener;

    public ArtsAdapter(List<ArtCard> arts, Context context) {
        this.arts = arts;
        this.context = context;
    }
    public void setOnClickListener(OnArtClickListener listener){
        this.listener = listener;
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

        holder.artCard = art;

        holder.tv_ut.setText(DateUtil.toDate(art.getUploadTime()));
        holder.tv_name.setText(art.getUserName());

        holder.tv_zan.setText(art.getFlower() + "");
        Glide.with(context)
                .load(art.getImUrl())
                .into(holder.im_art);
        Glide.with(context)
                .load(art.getAv())
                .into(holder.im_avatar);
    }

    @Override
    public int getItemCount() {
        return arts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.im_head_card) ImageView im_avatar;
        @Bind(R.id.tv_name_card) TextView tv_name;
        @Bind(R.id.tv_zan)TextView tv_zan;
        @Bind(R.id.im_art_art)ImageView im_art;
        @Bind(R.id.tv_uptime_art)TextView tv_ut;
        @Bind(R.id.im_love_art)ImageView im_love;
        ArtCard artCard;
        View artView;
        public ViewHolder(View itemView) {
            super(itemView);
            artView = itemView;
            ButterKnife.bind(this,artView);
            im_love.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onArtClickListener(view,im_art,im_love,artCard);
        }
    }

}
