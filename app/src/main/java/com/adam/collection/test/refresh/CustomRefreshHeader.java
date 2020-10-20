package com.adam.collection.test.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adam.collection.test.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

public class CustomRefreshHeader extends LinearLayout implements RefreshHeader{

    private static final String TAG = "CustomRefreshHeader";
    private View headerView;
    private ImageView animView;
    private TextView hintView;
    private AnimationDrawable animDrawable;

    public CustomRefreshHeader(Context context) {
        this(context, null);
    }

    public CustomRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        headerView = View.inflate(context, R.layout.custom_refresh_header, this);
        animView = (ImageView) headerView.findViewById(R.id.anim_img);
        hintView = (TextView) headerView.findViewById(R.id.hint_txt);
        animDrawable = (AnimationDrawable) animView.getBackground();
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        animDrawable.stop();
        animDrawable.selectDrawable(0);
        if(success) {
            hintView.setText("刷新数据成功");
        }else{
            hintView.setText("刷新数据失败");
        }
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState){
            case PullDownToRefresh:
                //下拉刷新开始。正在下拉还没松手时调用
                //每次重新下拉时，将图片资源重置为小人的大脑袋
                animDrawable.stop();
                animDrawable.selectDrawable(0);
                hintView.setText("下拉可以刷新");
                break;
            case Refreshing:
                //正在刷新。只调用一次
                //状态切换为正在刷新状态时
                hintView.setText("正在刷新数据");
                animView.post(new Runnable() {
                    @Override
                    public void run() {
                        animDrawable.start();
                    }
                });

                break;
            case ReleaseToRefresh:
                hintView.setText("松开刷新数据");
                break;
        }
    }

    public void setHeadTextColor(int color) {
        if (hintView == null) {
            return;
        }
        hintView.setTextColor(color);
    }

    public void setHeaderBackgroundColor(int backgroundColor) {
        View header = getChildAt(0);
        if (header == null) {
            return;
        }
        Log.e(TAG, "headerView: " + headerView.toString() + ", child 0: " + header.toString());
        header.setBackgroundColor(backgroundColor);
    }
}
