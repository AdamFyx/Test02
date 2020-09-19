package com.adam.collection.test.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Jerry
 * Generated at: 2019/10/17 14:04
 * WeChat: enGrave93
 * Description: Making the process of requesting permissions dynamically on Android platform in a productive and simple way.
 */
public class PermissionUtil {

    private static final String TAG = "PermissionUtil";

    public static void requestPermissions(Activity activity, int requestCode, String... permissions) {
        if (activity == null || permissions == null || permissions.length == 0) {
            Log.e(TAG, "null parameters -> " + (activity == null ? "activity" : "permissions"));
            return;
        }
        String[] permissionsToRequest = getPermissionsToRequest(activity, permissions);
        if (permissionsToRequest != null && permissionsToRequest.length > 0) {
            //ActivityCompat.requestPermissions(activity, permissionsToRequest, requestCode);
        } else {
            Log.e(TAG, "from " + activity.getClass().getSimpleName() + " all permissions are granted.");
        }
    }

    public static void requestPermissions(Fragment fragment, int requestCode, String... permissions) {
        if (fragment == null || permissions == null || permissions.length == 0) {
            Log.e(TAG, "null parameters -> " + (fragment == null ? "fragment" : "permissions"));
            return;
        }
//        String[] permissionsToRequest = getPermissionsToRequest(fragment.getContext(), permissions);
//        if (permissionsToRequest != null && permissionsToRequest.length > 0) {
//            //fragment.requestPermissions(permissionsToRequest, requestCode);
//        } else {
//            Log.e(TAG, "from " + fragment.getClass().getSimpleName() + " all permissions are granted.");
//        }
    }

    private static String[] getPermissionsToRequest(Context context, String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return null;
        }
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
//                deniedPermissions.add(permission);
//            }
        }
        return deniedPermissions.toArray(new String[0]);
    }
}
