package com.adam.collection.test.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.adam.collection.test.bean.Linker;
import com.adam.collection.test.camera.LogUtil;
import com.adam.collection.test.ui.MainActivity;
import com.adam.collection.test.util.ClipeBoardUtil;
import com.adam.collection.test.views.DragView;
import com.adam.collection.test.views.DrawViewCanvas;
import com.adam.collection.test.views.SurfaceViewTemplatePaint;
import com.adam.collection.test.web.ZtbWebview;

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
    View dragview;

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
        view.findViewById(R.id.threadlocal).setOnClickListener(this);
        view.findViewById(R.id.ztbwebview).setOnClickListener(this);
        view.findViewById(R.id.skip).setOnClickListener(this);
        DrawViewCanvas view1=new DrawViewCanvas(activity);
        dragview=view.findViewById(R.id.dragview);


        view1.setMinimumHeight(500);
        view1.setMinimumWidth(300);
        //通知view组件重绘，
        view1.invalidate();
        layout.addView(view1);
        return view;
    }
    private  ThreadLocal<Linker> mStringThreadLocal=new ThreadLocal<Linker>();
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear:
                SurfaceViewTemplatePaint.clearCanvas();
                break;
            case R.id.threadlocal:
                Linker linker1=new Linker();
                linker1.setName("小明");
                linker1.setNumber("1112255");
                Linker linker2=new Linker();
                linker2.setName("笑话");
                linker2.setNumber("1112255");
                Linker linker3=new Linker();
                linker3.setName("消防");
                linker3.setNumber("1112255");
                mStringThreadLocal.set(linker1);
                Log.d("threadlocal",mStringThreadLocal.get().getName());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mStringThreadLocal.set(linker2);
                        Log.d("threadlocal",mStringThreadLocal.get().getName());
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mStringThreadLocal.set(linker3);
                        Log.d("threadlocal",mStringThreadLocal.get().getName());
                    }
                }).start();
                break;
            case R.id.ztbwebview:
                ClipeBoardUtil.setClipeBoardContent(activity,"http://www.baidu.com");
                break;
            case R.id.skip:
                activity.getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        //把获取到的内容打印出来
                        Log.i("YoungerHu", ClipeBoardUtil.getClipeBoardContent(activity));
                            Intent intent=new Intent(activity,ZtbWebview.class);
                            intent.putExtra("url",ClipeBoardUtil.getClipeBoardContent(activity));
                            startActivity(intent);
                    }
                });
                break;
        }

    }


}
