package com.adam.collection.test.contract;

import com.adam.collection.test.base.IBasePresenter;
import com.adam.collection.test.base.IBaseView;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/2 17:18
 * <br/>
 *
 * @since
 */
/*
连接v层和p层的一个契约包
 */
public  interface MainContract {

    interface  View extends IBaseView{
        void  testGetMview();
    }
    interface  Presenter extends IBasePresenter<View> {
        void  testGetMpresenter();

        void testDb();

        void testRequestNetwork();

        void testPreference();
    }
}
