package com.adam.collection.test.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/19 15:13
 * 类似于qq的侧边栏
 * <br/>
 *
 * @since
 */
public class DragViewgroup  extends FrameLayout {
    private ViewDragHelper mViewDragHelper;
    private View mMenuView,mMainView;
    private  int mWidth;
    public DragViewgroup(@NonNull Context context) {
        super(context);
        initView();
    }

    public DragViewgroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragViewgroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public DragViewgroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView=getChildAt(0);
        mMainView=getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=mMenuView.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //讲触摸事件传递给ViewDragHelper,此操作不不可少
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
    private  void initView(){
        mViewDragHelper=ViewDragHelper.create(this,callback);
    }
    private  ViewDragHelper.Callback callback=new ViewDragHelper.Callback() {
        //何时开始检测触摸事件
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return mMainView==child;
        }
        //处理垂直滑动

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return 0;
        }
        //处理水平滑动
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }
        //拖动结束后调用
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //手指抬起后缓慢移动到指定位置
            if(mMainView.getLeft()<500){
                //关闭菜单
                //相当于Scroller的startScroll方法
                mViewDragHelper.smoothSlideViewTo(mMainView,0,0);
                ViewCompat.postInvalidateOnAnimation(DragViewgroup.this);
            }else{
                //打开菜单
                mViewDragHelper.smoothSlideViewTo(mMainView,300,0);
                ViewCompat.postInvalidateOnAnimation(DragViewgroup.this);
            }
        }
    };

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
