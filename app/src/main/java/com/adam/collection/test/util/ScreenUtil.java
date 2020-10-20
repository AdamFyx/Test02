package com.adam.collection.test.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Author: Adam Inc.
 * <br/>
 * Created at: 2020/10/20 13:48
 * <br/>
 *
 * @since
 */
public  class ScreenUtil {
    /**
     * 获取屏幕相关参数
     */
    public static DisplayMetrics getSceenSize(Context context){
        DisplayMetrics metrics=new DisplayMetrics();
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display=wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }
    /*
    获取屏幕density
     */
    public static  float getDeviceDenisity(Context context){
        DisplayMetrics metrics=new DisplayMetrics();
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }
}
