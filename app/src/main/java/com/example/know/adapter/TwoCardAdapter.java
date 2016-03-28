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

        holder.tv_zan.setText(card.selfie.getUserId()+"");

        holder.tv_uptime.setText(card.selfie.getUploadTime().toString());

        Glide.with(context)
                .load(card.selfie.getImUrl())
                .centerCrop()
                .into(holder.im_selfie);

    }

    @Override
    public int getItemCount() {
        return twoCards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.im_selfie)ImageView im_selfie ;
        @Bind(R.id.im_art)ImageView im_art;
        @Bind(R.id.tv_zan)TextView tv_zan;
        @Bind(R.id.tv_uptime)TextView tv_uptime;
        View cardView;
        TwoCard twoCard;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView;
            ButterKnife.bind(this,itemView);

            im_selfie.setOnClickListener(this);
            im_art.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onCardClick(view,im_selfie,im_art,twoCard);
        }
    }

}
