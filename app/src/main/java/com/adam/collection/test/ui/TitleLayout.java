package com.adam.collection.test.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.adam.collection.test.R;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/9 14:34
 * <br/>
 *
 * @since
 */
public  class TitleLayout  extends LinearLayout {

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
    }
}
