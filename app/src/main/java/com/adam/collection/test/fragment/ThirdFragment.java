package com.adam.collection.test.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.adam.collection.test.R;
import com.adam.collection.test.Thread.ThreadPoolManager;
import com.adam.collection.test.adapter.MbannerAdapter;
import com.adam.collection.test.bean.InfoBean;
import com.adam.collection.test.util.LogUtils;
import com.airbnb.lottie.LottieAnimationView;
import com.wjn.myview.activity.MainActivity;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

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
    List<InfoBean> data = new ArrayList<>();
    private Banner mbanner;
    LottieAnimationView lottieAnimationView;

    //    MarqueeText horseText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.third_fragment, container, false);
        mViewStub = view.findViewById(R.id.viewstub);
        view.findViewById(R.id.bugly).setOnClickListener(this);
        view.findViewById(R.id.threadPool).setOnClickListener(this);
        view.findViewById(R.id.skipCustomView).setOnClickListener(this);
        mbanner = view.findViewById(R.id.banner);
        //json动态图加载
        lottieAnimationView=view.findViewById(R.id.jsonimage);
        lottieAnimationView.setAnimation("data.json");
        lottieAnimationView.loop(false);
        lottieAnimationView.playAnimation();
        //显示方法1
        //mViewStub.setVisibility(View.VISIBLE);
        //显示方法2
        View infalteView = mViewStub.inflate();
        textView = infalteView.findViewById(R.id.hidetext);
        textView.setText("你好啊");
        //初始化数据
        initData();
        //色湖之mbanner适配器
        mbanner.setAdapter(new MbannerAdapter(activity, data));
        //是否允许自动轮播
        mbanner.isAutoLoop(true);
        //设置指示器，CircleIndicators已经定义好的类，直接用就好
        mbanner.setIndicator(new CircleIndicator(activity));
        mbanner.start();
        return view;
    }

    public void initData() {
        data.add(new InfoBean(R.drawable.aa));
        data.add(new InfoBean(R.drawable.bb));
        data.add(new InfoBean(R.drawable.cc));
        data.add(new InfoBean(R.drawable.dd));
        data.add(new InfoBean(R.drawable.ee));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bugly:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("adas");
                    }
                }).start();
                break;
            case R.id.threadPool:
                //实现线程池的功能
                for (int i = 0; i < 9; i++) {
                    ThreadPoolManager.getInstance().execute(new DownLoadTask(i));
                }
                break;
            case R.id.skipCustomView:
                startActivity(new Intent(activity, MainActivity.class));
                break;
        }
    }

    //模仿下载任务
    class DownLoadTask implements Runnable {
        private int num;

        public DownLoadTask(int num) {
            super();
            this.num = num;
            LogUtils.d("JAVA", "task - " + num + " 等待中...");
        }

        @Override
        public void run() {
            LogUtils.d("JAVA", "task - " + num + " 开始执行了...开始执行了...");
            SystemClock.sleep(5000); //模拟延时执行的时间
            LogUtils.e("JAVA", "task - " + num + " 结束了...");

        }
    }
}
