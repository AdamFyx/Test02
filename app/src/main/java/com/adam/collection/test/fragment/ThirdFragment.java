package com.adam.collection.test.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.adam.collection.test.R;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/16 13:43
 * <br/>
 *
 * @since
 */
public class ThirdFragment extends Fragment implements View.OnClickListener {
    Activity activity;
    ViewStub mViewStub;
    TextView textView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.third_fragment,container,false);
        mViewStub=view.findViewById(R.id.viewstub);
        view.findViewById(R.id.bugly).setOnClickListener(this);
        //显示方法1
        //mViewStub.setVisibility(View.VISIBLE);
        //显示方法2
        View infalteView=mViewStub.inflate();
        textView=infalteView.findViewById(R.id.hidetext);
        textView.setText("你好啊");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bugly:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("adas");
                    }
                }).start();
                 break;
        }
    }
}
