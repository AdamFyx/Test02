package com.adam.collection.test.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.adam.collection.test.R;
import com.adam.collection.test.Thread.ThreadPoolManager;
import com.adam.collection.test.adapter.MbannerAdapter;
import com.adam.collection.test.bean.InfoBean;
import com.adam.collection.test.listseekbar.ListSeekBarActivity;
import com.adam.collection.test.util.BitmapHelper;
import com.adam.collection.test.util.DiskLruCacheUtil;
import com.adam.collection.test.util.MemoryCache;
import com.adam.collection.test.util.LogUtils;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.util.LruCache;
import com.wjn.myview.activity.MainActivity;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.bloomfilter.bitMap.BitMap;

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
    LruCache<String, BitMap> mMemoryCache;
    ImageView imageView;
    ImageView imageView2;
    private DiskLruCacheUtil mDiskLruCacheUtil;
    TextView textView2;

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
        mDiskLruCacheUtil = new DiskLruCacheUtil(activity,"Locations");

        view.findViewById(R.id.bugly).setOnClickListener(this);
        view.findViewById(R.id.threadPool).setOnClickListener(this);
        view.findViewById(R.id.skipCustomView).setOnClickListener(this);
        view.findViewById(R.id.setLruCache).setOnClickListener(this);
        view.findViewById(R.id.getLruCache).setOnClickListener(this);
        view.findViewById(R.id.setDiskLru).setOnClickListener(this);
        view.findViewById(R.id.getDiskLru).setOnClickListener(this);
        view.findViewById(R.id.listseekbar).setOnClickListener(this);

        mbanner = view.findViewById(R.id.banner);
        imageView=view.findViewById(R.id.image);
        imageView2=view.findViewById(R.id.image2);
        textView2=view.findViewById(R.id.disklru);
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
        //色湖之mbanner适配器
        mbanner.setAdapter(new MbannerAdapter(activity, data));
        //是否允许自动轮播
        mbanner.isAutoLoop(true);
        //设置指示器，CircleIndicators已经定义好的类，直接用就好
        mbanner.setIndicator(new CircleIndicator(activity));
        mbanner.start();
//        windowmanager();
        return view;
    }
    //使用windows显示一个按钮
    public void windowmanager(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(activity)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
            } else {
                WindowManager mWindowManager=(WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);

                Button mFloatingButton=new Button(activity);
                mFloatingButton.setText("windowBtn");
                WindowManager.LayoutParams mLayoutParams=new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0,0,
                        PixelFormat.TRANSPARENT
                );
                mLayoutParams.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
                mLayoutParams.gravity= Gravity.LEFT|Gravity.TOP;
                mLayoutParams.x=100;
                mLayoutParams.y=300;
                mWindowManager.addView(mFloatingButton,mLayoutParams);            }
        }

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
            case R.id.setLruCache:
                //内存缓存
                Log.d("LruCache","我被执行到了");
                Bitmap bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.cc);
                MemoryCache.getInstance().addBitmapToLruCache("cc",bitmap);
                break;
            case R.id.getLruCache:
                imageView.setImageBitmap(MemoryCache.getInstance().getBitmapFromLruCache("cc"));
                break;
            case R.id.setDiskLru:
                //sd卡缓存
                Bitmap bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.aa);
                mDiskLruCacheUtil.put("bitmap", BitmapHelper.BitmapToByte(bitmap1));
                break;
            case R.id.getDiskLru:
                if(mDiskLruCacheUtil.getBytesCache("bitmap")!=null){
                    imageView2.setImageBitmap(BitmapHelper.ByteToBitmap(mDiskLruCacheUtil.getBytesCache("bitmap")));
                }
                break;
            case R.id.listseekbar:
                startActivity(new Intent(activity, ListSeekBarActivity.class));
                break;

        }
    }

    public void LruCache(){
        int maxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        int cachaeSize=maxMemory/8;
        mMemoryCache=new LruCache<String , BitMap>(cachaeSize){

        };
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
