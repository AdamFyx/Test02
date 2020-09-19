package com.adam.collection.test.util;

import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;

public class DensityUtils {
	private DensityUtils() {

	}

	/**
	 * dp转px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, float dp) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) + 0.5f);
	}

	public static int sp2px(Context context, float sp) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()) + 0.5f);
	}

	public static int px2dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	public static int px2sp(Context context, float px) {
		return (int) (px / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
	}

	public static Point getImgPoint(String url, Context context) {
		Point p = new Point();
		if (url.indexOf("/emotion/") != -1) {
			p.x = DensityUtils.dp2px(context, 12);
			p.y = DensityUtils.dp2px(context, 12);
		} else if (url.indexOf("/face/") != -1 && url.indexOf(".png") != -1) {
			p.x = DensityUtils.dp2px(context, 76);
			p.y = DensityUtils.dp2px(context, 42);
		} else if (url.indexOf("/face/") != -1) {
			p.x = DensityUtils.dp2px(context, 58);
			p.y = DensityUtils.dp2px(context, 20);
		} else {
			p.x = 0;
			p.y = 0;
		}

		return p;
	}
}
