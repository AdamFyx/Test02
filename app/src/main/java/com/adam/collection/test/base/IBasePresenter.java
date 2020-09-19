package com.adam.collection.test.base;


/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/2 16:39
 * <br/>
 *
 * @since
 */
public interface IBasePresenter<V extends IBaseView> {
    /**
     * 绑定View
     */
    void  attachView(V view);
}
