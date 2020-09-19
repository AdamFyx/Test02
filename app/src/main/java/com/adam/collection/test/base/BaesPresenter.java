package com.adam.collection.test.base;


import com.adam.collection.test.model.DataManager;
import com.adam.collection.test.model.db.AppDbHelper;
import com.adam.collection.test.model.db.DbHelper;
import com.adam.collection.test.model.http.ApiHelper;
import com.adam.collection.test.model.http.AppApiHelper;
import com.adam.collection.test.model.preference.AppPreferenceHelper;
import com.adam.collection.test.model.preference.PreferenceHelper;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/2 16:44
 * <br/>
 *
 * @since
 */
public abstract  class BaesPresenter<V extends IBaseView> implements IBasePresenter<V> {
    //数据管理，负责M层的业务逻辑操作
    protected DataManager mDataManager;
    //v层的引用
    protected  V mView;
    /**
     * 初始化DataManger
     */
    public  BaesPresenter(){
        //数据库
        AppDbHelper appDbHelper=new DbHelper();
        //共享参数存储
        AppPreferenceHelper appPreferenceHelper=new PreferenceHelper();
        //网络请求
        AppApiHelper appApiHelper=new ApiHelper();
        mDataManager=new DataManager(appDbHelper,appApiHelper,appPreferenceHelper);
    }

    /**
     * 绑定view
     */
    @Override
    public void attachView(V view) {
        this.mView=view;
    }
}
