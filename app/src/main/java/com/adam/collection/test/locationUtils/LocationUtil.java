package com.adam.collection.test.locationUtils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


/**
 * @author xys
 * @date 2020/9/2.
 */
public class LocationUtil {
    public static final String KEY_LOCATION_LON = "sp_location_longitude";
    public static final String KEY_LOCATION_LAT = "sp_location_latitude";
    public static final String KEY_LOCATION_ADDRESS = "sp_location_address";

    public static void startLocation(Context context, final OnLocationChangedListener listener) {
        AMapLocationClient mLocationClient = new AMapLocationClient(context.getApplicationContext());
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        listener.onLocationChanged(String.valueOf(aMapLocation.getLongitude()), String.valueOf(aMapLocation.getLatitude()), aMapLocation.getAddress());
                    } else {
                        Log.i("定位失败：ErrorCode:"+aMapLocation.getErrorCode(),", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
        mLocationClient.setLocationListener(mLocationListener);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(20000);
        mLocationClient.setLocationOption(mLocationOption);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

//    public static String getLongitude(){
//        return SPTool.getString(LocationUtil.KEY_LOCATION_LON, "");
//    }
//    public static String getLatitude(){
//        return SPTool.getString(LocationUtil.KEY_LOCATION_LAT, "");
//    }

    public static interface OnLocationChangedListener {
        void onLocationChanged(String lon, String lat, String address);
    }
}
