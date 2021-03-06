package com.adam.collection.test.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.adam.collection.test.R;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/15 17:16
 * <br/>
 *
 * @since
 */
public class proportion  extends View {
    private float mWidth;
    private float mHeight;
    //圆心坐标
    private float mCircleXY;
    //圆的半径
    private float mRadius;
    //弧线的外接矩形

    private RectF rectF;
    //画笔
    private Paint paint;
    private Paint textPaint;
    private Paint circlePaint;
    //最大进度
    private int mMaxProgress = 100;
    //当前进度
    private int mProgress = 30;
    //外层弧线的宽
    private int mCircleLineStrokeWidth;

    public proportion(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //得到视图的宽高取最小值给宽
        mWidth = getWidth();
        mHeight = getHeight();
        mWidth = Math.min(mWidth, mHeight);

        //确定圆心，半径，外弧的宽度
        mCircleXY = mWidth / 2;
        mRadius = mCircleXY / 2;
        mCircleLineStrokeWidth = (int) (mRadius / 2);

        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(0x00, 0xff, 0x00));
        canvas.drawColor(Color.TRANSPARENT);
        paint.setStrokeWidth(mCircleLineStrokeWidth);
        paint.setStyle(Paint.Style.STROKE);

        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.rgb(0x00, 0xff, 0x00));

        //绘制弧线，需要制定其椭圆的外接矩形
        rectF.left = mCircleLineStrokeWidth / 2;
        rectF.top = mCircleLineStrokeWidth / 2;
        rectF.right = mWidth - mCircleLineStrokeWidth / 2;
        rectF.bottom = mWidth - mCircleLineStrokeWidth / 2;

        //绘制圆
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, circlePaint);
        //绘制弧线
        canvas.drawArc(rectF, -90, ((float) mProgress / mMaxProgress) * 360, false, paint);
        //绘制文字
        String text = mProgress + "%";
        float showTextSize = mWidth / 10;
        textPaint.setTextSize(showTextSize);
        textPaint.setColor(Color.rgb(0xff, 0x00, 0x00));
        canvas.drawText(text, 0, text.length(), mCircleXY - (showTextSize * text.length()) / 4, mCircleXY + showTextSize / 3, textPaint);

    }

    //对外提供设置进度的方法
    public void setmProgress(int progress) {
        if (progress >= 0) {
            mProgress = progress;
        } else {
            mProgress = 0;
        }
        this.invalidate();

    }
}