package com.adam.collection.test.ui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.adam.collection.test.util.AppUtils;
import com.adam.collection.test.util.LogUtils;
import com.adam.collection.test.util.MyUtils;
import com.adam.collection.test.util.UmengHelper;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/16 10:49
 * <br/>
 *
 * @since
 */
public  class MyAppliaction  extends Application {
    public static MyAppliaction baseApplication;

    public static Context context;

    public static MyAppliaction getInstance() {
        if (baseApplication == null) {
            baseApplication = new MyAppliaction();
        }
        return baseApplication;
    }
    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(getBaseContext());
        context = getApplicationContext();
        String processName = getCurProcessName(context);
        LogUtils.d("MyAppliaction",processName);
        UmengHelper.getInstance().init(this,"MainActivity");

        initBugly();
    }
    /*
    初始化腾讯bugly管理
     */
    private void initBugly(){
        /* Bugly SDK初始化
         * 参数1：上下文对象
         * 参数2：APPID，平台注册时得到,注意替换成你的appId
         * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
         * 注意：如果您之前使用过Bugly SDK，请将以下这句注释掉。
         */
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(AppUtils.getVersionName(context));
        strategy.setAppPackageName("com.adam.collection.test");
        strategy.setAppReportDelay(2000);                          //Bugly会在启动20s后联网同步数据

        /*  第三个参数为SDK调试模式开关，调试模式的行为特性如下：
            输出详细的Bugly SDK的Log；
            每一条Crash都会被立即上报；
            自定义日志将会在Logcat中输出。
            建议在测试阶段建议设置成true，发布时设置为false。*/

        CrashReport.initCrashReport(getApplicationContext(), "137ea58e68", true ,strategy);

        //Bugly.init(getApplicationContext(), "137ea58e68", false);
    }
        /*
        获取当前进程名
         */
       String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
           return null;
    }

}