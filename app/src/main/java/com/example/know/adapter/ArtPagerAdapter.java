package com.example.know.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.know.artist.R;
import com.example.know.model.ArtCard;
import com.example.know.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2017/1/26.
 */

public class ArtPagerAdapter extends PagerAdapter {


    private List<CardView> cardviews;
    private List<ArtCard> artCards;
    private float mBaseElevation;
    Context context;

    public void setOnArtClickListener(OnArtClickListener onArtClickListener) {
        this.onArtClickListener = onArtClickListener;
    }

    OnArtClickListener onArtClickListener;

    public ArtPagerAdapter(Context context,List<ArtCard> artCards) {
        this.context = context;
        cardviews = new ArrayList<>();
        this.artCards = artCards;
        for (int i = 0;i<artCards.size();i++){
            cardviews.add(null);
        }
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    public CardView getCardViewAt(int position) {
        return cardviews.get(position);
    }

    @Override
    public int getCount() {
        return artCards.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.viewcard_art,container,false);
        container.addView(view);

        bindCard(artCards.get(position),view);

        CardView cardView = (CardView) view.findViewById(R.id.cv_art);

        if (mBaseElevation == 0){
            Log.e("elevation","------------->");
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation*8);
        cardviews.set(position,cardView);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        cardviews.set(position,null);
    }

    private void bindCard(ArtCard artCard,View view){

        TextView nameText = (TextView) view.findViewById(R.id.tv_name_card);
        TextView timeText = (TextView) view.findViewById(R.id.tv_uptime_art);
        ImageView artImage = (ImageView) view.findViewById(R.id.im_art_art);
        ImageView loveImage = (ImageView) view.findViewById(R.id.im_love_art);
        TextView loveText = (TextView) view.findViewById(R.id.tv_zan);

        Glide.with(context)
                .load(artCard.getImUrl())
                .into(artImage);

        nameText.setText(artCard.getUserName());
        timeText.setText(DateUtil.toDate(artCard.getUploadTime()));
        loveText.setText(artCard.getFlower()+"");
        if (artCard.getLovely()>0){
            loveImage.setImageResource(R.drawable.ic_flower_24dp_yellow);
        }

        artImage.setOnClickListener(view1 -> {
            onArtClickListener.onArtClickListener(view1,artImage,loveImage,artCard);
        });
        loveImage.setOnClickListener(view1 -> {
            onArtClickListener.onArtClickListener(view1,artImage,loveImage,artCard);
        });

    }

    public void setArtCards(List<ArtCard> artCardss) {
        this.artCards.clear();
        this.artCards.addAll(artCardss);
        cardviews = new ArrayList<>();
        for (int i = 0;i<artCardss.size();i++){
            cardviews.add(null);
        }

        notifyDataSetChanged();

    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }
}
