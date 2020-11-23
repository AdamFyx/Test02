package com.adam.collection.test.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/19 11:54
 * <br/>
 *
 * @since
 */
public  class DragView extends View {

    private  int lastX;
    private  int lastY;
    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) (event.getRawX());
        int rawY = (int) (event.getRawY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录触摸点坐标
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;
                //在当前left,top,right,bottom的基础上加上偏移量
                //方式1：
                offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);

                //方式2：
//                layout(
//                        getLeft() + offsetX,
//                        getTop() + offsetY,
//                        getRight() + offsetX,
//                        getBottom() + offsetY);
                //重新设置初始坐标
                lastX=rawX;
                lastY=rawY;
                break;

        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }


}
