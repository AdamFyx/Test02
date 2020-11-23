package com.adam.collection.test.listseekbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class CustomSeekBar extends android.view.View {
    /**
     * 除文字以外的paint对象
     */
    Paint paint;
    /**
     * 用于处理文字的paint对象
     */
    Paint paintText;
    /**
     * 传入的实体类实例（因view未测量好而暂存于此）
     */
    SeekBarBean mTemporarySeekBarBean;
    /**
     * 与view绑定的实体类实例
     */
    SeekBarBean mSeekBarBean;
    /**
     * 图行混合滤镜
     */
    PorterDuffColorFilter porterDuffColorFilter;
    /**
     * 刻度值间隔
     */
    int mScaleSpace;
    /**
     * 点击事件时的坐标
     */
    float ACTION_DOWN_x;
    float ACTION_DOWN_y;
    /**
     * 进度条的getleft和getright
     */
    float RectX_left;
    float RectX_right;
    /**
     * 刻度尺运算值(最大值为17)
     */
    int mSeekTime;
    Bitmap bitmap;

    /**
     * 图标距离进度条顶的距离
     */
    SeekBarListener seekBarListener;
    /**
     * 图标距离进度条左边的距离
     */
    static int sIconMarginLeft=15;
    /**
     * 图标距离进度条顶的距离
     */
    static int sIconMarginTop=6;
    /**
     * 图标长高
     */
    static int sIconSpecifications=36;
    /**
     * 刻度尺左边距
     */
    static int sMarginLeft=12;
    /**
     * 刻度尺右边距
     */
    static int sMarginRight=40;
    /**
     * 刻度尺刻度数量(刻度值-1)
     */
    static int sScalesCount=17;
    /**
     * view最低宽
     */
    static int sViewWidth=100;
    /**
     * view最低高
     */
    static int sViewHeight=45;
    /**
     * 进度条在线/离线颜色
     */
    static String onLineColor="#308FFD";
    static String offLineColor="#AEBAD0";
    /**
     * 回调接口，用作Action_Up最后一步调用。
     */
    interface  SeekBarListener{
         void beforeActionUpFinish();
    }

    public void setSeekBarListener(SeekBarListener seekBarListener){
        this.seekBarListener=seekBarListener;
    }


    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaints();
        porterDuffColorFilter= new PorterDuffColorFilter(android.graphics.Color.parseColor("#55000000"), PorterDuff.Mode.DARKEN);
    }

    private void initPaints() {
        paint=new Paint();
        paint.setColor(android.graphics.Color.GRAY);
        paint.setStrokeWidth((float) 6.0);
        paint.setStyle(Paint.Style.FILL);
        paintText=new Paint();
        paintText.setTextSize(30);
        paintText.setColor(android.graphics.Color.GRAY);
        paintText.setAntiAlias(true);
        paintText.setDither(true);
        paintText.setStyle(Paint.Style.STROKE);
    }

    public CustomSeekBar(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode==MeasureSpec.AT_MOST||heightSpecMode==MeasureSpec.AT_MOST){
            int widthSize= widthSpecMode==MeasureSpec.AT_MOST?dip2px(sViewWidth) : MeasureSpec.getSize(widthMeasureSpec);
            int heightSize=heightSpecMode==MeasureSpec.AT_MOST?dip2px(sViewHeight) : MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(widthSize, heightSize);
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mScaleSpace=(int)((getWidth()-dip2px(sMarginLeft+sMarginRight))/sScalesCount);
        if (mTemporarySeekBarBean!=null){
            setSeekBarBean(mTemporarySeekBarBean);
            mTemporarySeekBarBean=null;
        }
        onDrawSeekBar(canvas,mSeekBarBean);


    }

    /**
     * 根据是否在线而修改画笔的颜色，进而绘出相应颜色的进度条。
     * 如果isACTION_DOWN()返回true，意味着获得焦点，会进行图行混合滤镜，让进度条变暗，实现点击效果。
     */
    private void onDrawSeekBar(Canvas canvas, SeekBarBean seekBarBean){
        if (seekBarBean.isOnline){
            paint.setColor(android.graphics.Color.parseColor(onLineColor));
        }else {
            paint.setColor(android.graphics.Color.parseColor(offLineColor));
        }
        if (seekBarBean.isACTION_DOWN()){
            paint.setColorFilter(porterDuffColorFilter);

        }
        bitmap = BitmapFactory.decodeResource(getContext().getResources(),seekBarBean.iconId);
        bitmap=Bitmap.createScaledBitmap(bitmap, dip2px(sIconSpecifications), dip2px(sIconSpecifications), false);
        canvas.drawRect(dip2px(sMarginLeft)+seekBarBean.getTime()*mScaleSpace,getTop(),getWidth(),getBottom(),paint);
        paint.setColorFilter(null);
        canvas.drawBitmap(bitmap,dip2px(sIconMarginLeft)+seekBarBean.getTime()*mScaleSpace,getTop()+dip2px(sIconMarginTop),paint);

    }


    private int dip2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * ACTION_DOWN记录当前坐标，如果x坐标大于进度条当前getleft值，意味着进度条获得焦点，记录此时的进度条的刻度值，用于ACTION_MOVE事件计算距离。
     * ACTION_MOVE如果x坐标相对于点击事件的x坐标的距离 比一个刻度值还大，就会修改进度条的刻度值，并调用重绘
     * ACTION_UP更新RectX_left的距离，并回调方法（用于适配器），用于自动排序。
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        int distance;
        int timeChange;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    ACTION_DOWN_x=x;
                    ACTION_DOWN_y=y;
                    if (x>RectX_left){
                            mSeekBarBean.isACTION_DOWN=true;
                            mSeekTime=mSeekBarBean.getTime();
                            invalidate();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mSeekBarBean.isACTION_DOWN()){
                        distance=(int)(x-ACTION_DOWN_x);
                        if (distance!=0){
                            timeChange=distance/mScaleSpace;
                            mSeekBarBean.setTime(mSeekTime+timeChange);
                            invalidate();
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (mSeekBarBean.isACTION_DOWN()){
                        RectX_left=(dip2px(sMarginLeft)+mSeekBarBean.getTime()*mScaleSpace);
                        mSeekBarBean.isACTION_DOWN=false;
                        invalidate();
                        seekBarListener.beforeActionUpFinish();
                    }

                    break;
            }
        return true;
    }

    /**
     * 如果测量未完成(getWidth等于0)，则先将实例存储起来，等到onDraw再调用一次该方法。
     * 如果已经测量完，则记录初始化进度条的长度。
     * @param seekBarBean:处理后的实体类实例
     *
     */
    public void setSeekBarBean(SeekBarBean seekBarBean){
        if (getWidth()==0){
            this.mTemporarySeekBarBean=seekBarBean;
        }else {
            mSeekBarBean = seekBarBean;
            seekBarBean=null;
            RectX_left=dip2px(sMarginLeft)+mSeekBarBean.getTime()*mScaleSpace;
            RectX_right=getWidth();
        }
    }



}
