package com.adam.collection.test.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

public class AppUtils {
	
	private AppUtils(){
		
	}
	
	public static String getVersionName(Context context){
		String version="";
		try {
			PackageInfo info=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			version=info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}
	
	public static int getVersionCode(Context context){
		int version=0;
		try {
			PackageInfo info=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			version=info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}
	
	public static String getIntVersionName(Context context) {
		String version = "0.0";
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			version = packInfo.versionName;
			version = version.replace(".", "");
		} catch (Exception e) {
		}
		return version;
	}

	public static String getAppUserAgent(Context context){
		String userAgent=SPUtils.getString(context, "save_agent", "android");
		if(userAgent.equals("android")){
			WebView webView=new WebView(context);
			userAgent=webView.getSettings().getUserAgentString();
			SPUtils.putString(context, "save_agent", userAgent);
		}
		return userAgent;
	}

	public static String getCommonParams(Activity activity){
		String params="mobile=105" +
				"&version=" + AppUtils.getIntVersionName(activity) +
				"&user_id=" + SPUtils.getString(activity, ConstantsUtils.USER_ID, "0") +
				"&city_id=" + SPUtils.getString(activity, ConstantsUtils.HOME_CITY_CODE, "0") +
				"&lat=" + SPUtils.getString(activity, ConstantsUtils.LOCATION_LATITUDE, "0") +
				"&lon=" + SPUtils.getString(activity, ConstantsUtils.LOCATION_LONGITUDE, "0");

		return  params;
	}

    public static String getCommonParams(Context context){
        String params="mobile=105" +
                "&version=" + AppUtils.getIntVersionName(context) +
                "&user_id=" + SPUtils.getString(context, ConstantsUtils.USER_ID, "0") +
                "&city_id=" + SPUtils.getString(context, ConstantsUtils.HOME_CITY_CODE, "0") +
                "&lat=" + SPUtils.getString(context, ConstantsUtils.LOCATION_LATITUDE, "0") +
                "&lon=" + SPUtils.getString(context, ConstantsUtils.LOCATION_LONGITUDE, "0");

        return  params;
    }

	public static String getCommonParamsUrl(Activity activity, String url){
		String finalUrl=url;
		String params="mobile=105" +
				"&version=" + AppUtils.getIntVersionName(activity) +
				"&user_id=" + SPUtils.getString(activity, ConstantsUtils.USER_ID, "0") +
				"&city_id=" + SPUtils.getString(activity, ConstantsUtils.HOME_CITY_CODE, "0") +
				"&lat=" + SPUtils.getString(activity, ConstantsUtils.LOCATION_LATITUDE, "0") +
				"&lon=" + SPUtils.getString(activity, ConstantsUtils.LOCATION_LONGITUDE, "0");

		if(finalUrl.contains("?")){
			finalUrl=finalUrl+"&"+params;
		}else{
			finalUrl=finalUrl+"?"+params;
		}
		return finalUrl;
	}

	public static String getClipboard(Context context){
		ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData cd = cm.getPrimaryClip();
		String clipboard="";
		if(cd!=null){
			ClipData.Item item = cd.getItemAt(0);
            if(item!=null) {
                CharSequence cs = item.getText();
                if (cs != null) {
                    String str = cs.toString().trim();
                    clipboard = getEncoderUrl(str);
                }
            }
		}
		return clipboard;
	}

	public static void setClipboard(Context context, String clipboard){
		ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData cd = cm.getPrimaryClip();
		if(cd!=null){
			ClipData clear = ClipData.newPlainText("text", clipboard);
			cm.setPrimaryClip(clear);
		}
	}

	public static String getEncoderUrl(String url){
		String enCoderUrl="";
		try {
			enCoderUrl = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return enCoderUrl;
	}

	public static String getDecoderUrl(String url){
		String dnCoderUrl="";
		try {
			dnCoderUrl = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return dnCoderUrl;
	}

	public static int getStatusBarHeight(Activity activity){
		int result = 0;
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = activity.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
}
