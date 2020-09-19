package com.adam.collection.test.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.adam.collection.test.ui.MyAppliaction;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DeviceUtils {
	private DeviceUtils() {

	}

	/**
	 * 获取屏幕的宽（px）
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕的高（px）
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 获取设备信息
	 * 
	 * @param context
	 * @return
	 */
	public static String[] getDeviceInfo(Context context) {
		// 现在接口中记录的deviceId暂时没有用到
		String[] info = { "0", "0" };
		final String TAG = "DeviceUtils";
		String uuid = AppUtils.getUUID();
		if (context == null) {
			Log.e(TAG, "getDeviceInfo(Context context) -> Null context.");
			info[0] = uuid;
			return info;
		}
		if (ContextCompat.checkSelfPermission(MyAppliaction.context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			Log.e(TAG, "getDeviceInfo(Context context) -> Permission denied.");
			info[0] = uuid;
			return info;
		}
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) {
			Log.e(TAG, "getDeviceInfo(Context context) -> Null TelephonyManager.");
			info[0] = uuid;
			return info;
		}
		info[0] = tm.getDeviceId();
		info[1] = tm.getSubscriberId();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && TextUtils.isEmpty(info[0])) {
			// 拿到权限但是返回null生成一个随机的uuid
			Log.e(TAG, "getDeviceInfo(Context context) -> The current OS version is above Android Q, and the deviceId is null or a placeholder.");
			info[0] = uuid;
		}
		return info;
	}

	/**
	 * 获取context宽高
	 */
	public static Point getDeviceSize(Context ctx) {
		DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		Point size = new Point();
		size.x = dm.widthPixels;
		size.y = dm.heightPixels;
		return size;
	}
	
	
	public static String getDeviceModel(){
		String phoneModel="";
		try {
			phoneModel = URLEncoder.encode(android.os.Build.MODEL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return phoneModel;
	}

	public static int getAndroidSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

}
