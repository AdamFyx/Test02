package com.adam.collection.test.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import androidx.fragment.app.Fragment;

import com.adam.collection.test.R;
import com.adam.collection.test.views.DragView;
import com.adam.collection.test.views.DrawViewCanvas;
import com.adam.collection.test.views.SurfaceViewTemplatePaint;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/16 13:43
 * <br/>
 *
 * @since
 */
public class SecondFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private  int lastX=0;
    private  int lastY=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.second_fragment,container,false);
        LinearLayout layout=view.findViewById(R.id.canvas);
        view.findViewById(R.id.clear).setOnClickListener(this);
        DrawViewCanvas view1=new DrawViewCanvas(activity);
        view1.setMinimumHeight(500);
        view1.setMinimumWidth(300);
        //通知view组件重绘，
        view1.invalidate();
        layout.addView(view1);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear:
                SurfaceViewTemplatePaint.clearCanvas();
                break;
        }
    }
}
