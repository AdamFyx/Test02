package com.adam.collection.test.ui;

import android.app.Application;
import android.content.Context;

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
    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

    }

}
