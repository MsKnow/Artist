package com.example.know.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * Created by yang on 2017/1/25.
 */
public class ArtCardView extends RelativeLayout {
    public ArtCardView(Context context) {
        super(context);
    }

    public ArtCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //LayoutInflater.from(context).inflate()

    }

    public ArtCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
}
