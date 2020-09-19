package com.adam.collection.test.scan;

import android.app.Activity;
import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * 防止内存释放
 * @author Administrator
 * @param <T>
 */
public class WeakHandler<T extends Activity> extends Handler {
	public WeakReference<T> activiceReference;

	public WeakHandler(T activity) {
		activiceReference = new WeakReference<T>(activity);
	}
}
