package com.adam.collection.test.listseekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class ScaleView extends android.view.View {
    static String[] strings=new String[]{"立即","1s","2s","3s","4s","5s","6s","7s","8s","9s","10s","20s","30s","1m","2m","3m","4m","5m"};
    Paint paint;
    Paint paintText;
    int span;
    int max;
    public ScaleView(Context context) {
        super(context);
    }

    public ScaleView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setColor(android.graphics.Color.GRAY);
        paint.setStrokeWidth( dip2px(2));
        paint.setStyle(Paint.Style.FILL);
        paintText=new Paint();
        paintText.setTextSize(30);
        paintText.setColor(android.graphics.Color.GRAY);
        paintText.setAntiAlias(true);
        paintText.setDither(true);
        paintText.setStyle(Paint.Style.STROKE);
    }

    public ScaleView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode==MeasureSpec.AT_MOST||heightSpecMode==MeasureSpec.AT_MOST){
            int widthSize= widthSpecMode==MeasureSpec.AT_MOST?dip2px(100) : MeasureSpec.getSize(widthMeasureSpec);
            int heightSize=heightSpecMode==MeasureSpec.AT_MOST?dip2px(30) : MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(widthSize, heightSize);
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        span=(int)((getWidth()-dip2px(52))/17);
        max=span*17;
        onDrawScale(canvas);
        super.onDraw(canvas);
    }

    private void onDrawScale(Canvas canvas){
        paint.setColor(android.graphics.Color.parseColor("#AEBAD0"));
        for (int i=0;i<18;i++){
            canvas.drawLine(dip2px(12)+i*span,dip2px(30),dip2px(12)+i*span,dip2px(20),paint);
            canvas.drawText(strings[i], i*span+15, dip2px(15), paintText);
        }
        paint.setColor(android.graphics.Color.parseColor("#308FFD"));
        canvas.drawLine(dip2px(12), dip2px(30), getWidth(),  dip2px(30),paint);
    }
    private int dip2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
