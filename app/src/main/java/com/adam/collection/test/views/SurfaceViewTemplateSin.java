package com.adam.collection.test.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Surfaceview绘制图片
 * Created at: 2020/10/19 16:49
 * <br/>
 *
 * @since
 */
public  class SurfaceViewTemplateSin extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private  SurfaceHolder mHolder;
    //用于绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private  boolean mIsDrawing;
    private  int x;
    private  int y;
    private Path mpath;
    private Paint mpaint;


    public SurfaceViewTemplateSin(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewTemplateSin(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplateSin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

   private  void initView(){
        mHolder=getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);

        mpath=new Path();
        mpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setColor(Color.RED);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(10);
        mpaint.setStrokeCap(Paint.Cap.ROUND);
        mpaint.setStrokeJoin(Paint.Join.ROUND);
   }

    @Override
    public void run() {
        while(mIsDrawing){
            draw();
            //正弦曲线
            x+=1;
            y=(int)(100*Math.sin(x*2*Math.PI/180)+400);
            mpath.lineTo(x,y);
        }
    }
    private  void draw(){
        try {
            mCanvas=mHolder.lockCanvas();
            //SurFaceView背景
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mpath,mpaint);
        }catch (Exception e){

        }finally {
            if(mCanvas!=null){
                //保证每次都能将内容提交
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        mIsDrawing=true;
        mpath.moveTo(0,400);
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            mIsDrawing=false;
    }
}
