package com.adam.collection.test.model.db;

import android.util.Log;


/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/2 17:48
 * <br/>
 *
 * @since
 */
 public  class DbHelper implements AppDbHelper {
    @Override
    public void testDb() {
        Log.d("print","数据库操作");
    }
}
