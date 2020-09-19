package com.adam.collection.test.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/2 16:47
 * <br/>
 *
 * @since
 */
public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    //p层的应用
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //初始化mpreseter
        initPresenter();
        //绑定view
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
       // requestPermission();
        //初始化
        initView();
    }

    /*
        初始化mPresenter
     */
    protected abstract void initPresenter();

    /*
    初始化
     */
    protected abstract void initView();

    /*
    获取布局id
     */
    protected abstract int getLayoutId();
    /*
    获取定位权限
     */
}
