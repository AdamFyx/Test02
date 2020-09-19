package com.adam.collection.test.model;


import com.adam.collection.test.model.db.AppDbHelper;
import com.adam.collection.test.model.http.AppApiHelper;
import com.adam.collection.test.model.preference.AppPreferenceHelper;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/2 17:46
 * <br/>
 *
 * @since
 */
public  class DataManager implements AppDbHelper, AppApiHelper, AppPreferenceHelper {
   private  AppDbHelper mAppDbHelper;
   private  AppApiHelper mAppApiHelper;
   private  AppPreferenceHelper mAppPreferenceHelper;

   public  DataManager(AppDbHelper mAppDbHelper,AppApiHelper mAppApiHelper,AppPreferenceHelper mAppPreferenceHelper){
       this.mAppApiHelper=mAppApiHelper;
       this.mAppDbHelper=mAppDbHelper;
       this.mAppPreferenceHelper=mAppPreferenceHelper;
   }

    @Override
    public void testDb() {
        mAppDbHelper.testDb();
    }

    @Override
    public void testRequestNetwork() {
        mAppApiHelper.testRequestNetwork();
    }

    @Override
    public void testPreference() {
        mAppPreferenceHelper.testPreference();
    }
}
