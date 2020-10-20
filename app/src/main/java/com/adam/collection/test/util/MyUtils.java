package com.adam.collection.test.util;

import android.app.Activity;
import android.view.View;

import com.adam.collection.test.R;
import com.adam.collection.test.base.BaesPresenter;
import com.adam.collection.test.base.BaseActivity;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/21 11:24
 * <br/>
 *
 * @since
 */
public class MyUtils extends BaseActivity<BaesPresenter> {
   public static MyUtils myUtils;
    public static MyUtils getInstance() {
        if (myUtils == null) {
            myUtils = new MyUtils();
        }
        return myUtils;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
