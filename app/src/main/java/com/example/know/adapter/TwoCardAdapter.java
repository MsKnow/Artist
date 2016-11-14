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
import com.example.know.model.TwoCard;
import com.example.know.util.DateUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by know on 2016/3/27.
 */
public class TwoCardAdapter extends RecyclerView.Adapter<TwoCardAdapter.ViewHolder> {

    List<TwoCard> twoCards;
    Context context;
    OnCardClickListener listener;

    public TwoCardAdapter(List<TwoCard> twoCards, Context context) {
        this.twoCards = twoCards;
        this.context = context;
    }

    public void setOClickListener(OnCardClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_twocard,parent,false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TwoCard card = twoCards.get(position);

        holder.twoCard = card;

        holder.tv_name.setText(card.getUserName());
        if (card.art != null)
        holder.tv_zan.setText(card.art.getFlower() + "");
        String selfTime = DateUtil.toDate(card.selfie.getUploadTime());
        holder.tv_uptime.setText(selfTime);
        //holder.tv_uptime.setText(card.selfie.getUploadTime().toString()+"");
        Glide.with(context)
                .load(card.selfie.getImUrl())
                .centerCrop()
                .into(holder.im_selfie);
        holder.im_art.setImageResource(R.mipmap.ic_launcher);
        if(card.art!=null){
            Glide.with(context)
                    .load(card.art.getImUrl())
                    .centerCrop()
                    .into(holder.im_art);
        }
        Glide.with(context)
                .load(card.getAv())
                .centerCrop()
                .into(holder.im_avatar);

    }

    @Override
    public int getItemCount() {
        return twoCards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.im_head_card) ImageView im_avatar;
        @Bind(R.id.tv_name_card) TextView tv_name;

        @Bind(R.id.im_selfie)ImageView im_selfie ;
        @Bind(R.id.im_art)ImageView im_art;
        @Bind(R.id.tv_zan)TextView tv_zan;
        @Bind(R.id.im_love_two)ImageView im_love;
        @Bind(R.id.tv_uptime)TextView tv_uptime;
        View cardView;
        TwoCard twoCard;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView;
            ButterKnife.bind(this,itemView);

            im_selfie.setOnClickListener(this);
            im_art.setOnClickListener(this);
            im_love.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onCardClick(view,im_selfie,im_art,im_love,twoCard);
        }
    }

}
