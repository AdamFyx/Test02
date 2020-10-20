package com.adam.collection.test.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/9 11:30
 * <br/>
 *
 * @since
 */
public class ActivityController {
    public  static List<Activity> activities=new ArrayList<>();
    public static  void  addActivity(Activity activity){
        activities.add(activity);
    }
    public static  void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static  void  finishAll(){
        for (Activity activity:activities ){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
