package com.adam.collection.test.presenter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import com.adam.collection.test.base.BaesPresenter;
import com.adam.collection.test.contract.MainContract;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/14 9:56
 * <br/>
 *
 * @since
 */
public  class SecondPresenter extends BaesPresenter<MainContract.View> implements MainContract.Presenter{
    @Override
    public void testGetMpresenter() {

    }

    @Override
    public void testDb() {

    }

    @Override
    public void testRequestNetwork() {

    }

    @Override
    public void testPreference() {

    }
}
