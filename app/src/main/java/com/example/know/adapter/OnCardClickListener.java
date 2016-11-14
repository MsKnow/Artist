package com.example.know.adapter;

import android.view.View;

import com.example.know.model.TwoCard;

/**
 * Created by know on 2016/3/27.
 */
public interface OnCardClickListener {
    void onCardClick(View v, View selfCard, View atrCard,View loveImg, TwoCard twoCard);
}
