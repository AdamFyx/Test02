package com.adam.collection.test.listseekbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.adam.collection.test.R;


public class SeekBarListView extends FrameLayout  {
    android.widget.ListView listView;

    public SeekBarListView(Context context) {
        super(context);
    }

    public SeekBarListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.seekbarlistview, this, true);
        listView=(android.widget.ListView) findViewById(R.id.lv);

    }

    public SeekBarListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int mTotalHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childView = getChildAt(i);
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();
            childView.layout(left, mTotalHeight, measuredWidth, mTotalHeight + measureHeight);
            mTotalHeight += measureHeight;
        }
    }


    public void setAdapter( SeekBarAdapter seekBarAdapter){
        listView.setAdapter(seekBarAdapter);
    }



}
