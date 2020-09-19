/**
 *  2014-7-15   上午11:14:21
 *  Created By niexiaoqiang
 */

package com.adam.collection.test.scan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.View;

import com.adam.collection.test.R;
import com.adam.collection.test.util.DensityUtils;

import java.io.InputStream;

/**
 * 查找框--支持横屏
 * @author niexiaoqiang
 */
public class FinderView extends View {
	private static final long ANIMATION_DELAY = 30;
	private Paint finderMaskPaint;

	private Rect topRect = new Rect();
	private Rect bottomRect = new Rect();
	private Rect rightRect = new Rect();
	private Rect leftRect = new Rect();
	private Rect middleRect = new Rect();

	private Rect lineRect = new Rect();
	//	private Drawable zx_code_kuang;
//	private Drawable zx_code_line;
	private int lineHeight;

	private int measureedWidth;
	private int measureedHeight;
	private int scanBarColor;

	private Bitmap scanLineBitmap = null;

	/**
	 * 中间滑动线的最顶端位置
	 */
	private int slideTop;

	/**
	 * 四个绿色边角对应的长度
	 */
	private int ScreenRate;

	/**
	 * 四个绿色边角对应的宽度
	 */
	private static final int CORNER_WIDTH = 6;

	private Context context;
	private boolean isFirst=true;
	/**
	 * 中间那条线每次刷新移动的距离
	 */
	private static final int SPEEN_DISTANCE = 5;
	/**
	 * 字体大小
	 */
	private static final int TEXT_SIZE = 16;

	private int finder_mask;

	public FinderView(Context context) {
		super(context);
		init(context);
	}

	public FinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		this.context=context;
		finder_mask = context.getResources().getColor(R.color.viewfinder_mask);
		finderMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		finderMaskPaint.setColor(finder_mask);
//		zx_code_kuang = context.getResources().getDrawable(R.drawable.zx_code_kuang);
//		zx_code_line = context.getResources().getDrawable(R.drawable.zx_code_line);
		lineHeight = DensityUtils.dp2px(context, 2);
		scanBarColor = getResources().getColor(R.color.scan_bar_color);
		ScreenRate = DensityUtils.dp2px(context,20);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isFirst) {
			isFirst = false;
			slideTop = middleRect.top + CORNER_WIDTH;
//            slideBottom = frame.bottom - CORNER_WIDTH;
			int width = middleRect.right - middleRect.left;
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
			opt.inPurgeable = true;
			opt.inInputShareable = true;

			InputStream is = context.getResources().openRawResource(R.raw.scan_line);
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
			scanLineBitmap = Bitmap.createScaledBitmap(bitmap, width, DensityUtils.dp2px(context, 2), true);
		}

		finderMaskPaint.setColor(finder_mask);
		canvas.drawRect(leftRect, finderMaskPaint);
		canvas.drawRect(topRect, finderMaskPaint);
		canvas.drawRect(rightRect, finderMaskPaint);
		canvas.drawRect(bottomRect, finderMaskPaint);

		finderMaskPaint.setColor(scanBarColor);
		canvas.drawRect(middleRect.left - CORNER_WIDTH, middleRect.top - CORNER_WIDTH, middleRect.left + ScreenRate,
				middleRect.top, finderMaskPaint);
		canvas.drawRect(middleRect.left - CORNER_WIDTH, middleRect.top, middleRect.left, middleRect.top
				+ ScreenRate, finderMaskPaint);
		canvas.drawRect(middleRect.right - ScreenRate, middleRect.top - CORNER_WIDTH, middleRect.right + CORNER_WIDTH,
				middleRect.top, finderMaskPaint);
		canvas.drawRect(middleRect.right, middleRect.top, middleRect.right + CORNER_WIDTH, middleRect.top
				+ ScreenRate, finderMaskPaint);
		canvas.drawRect(middleRect.left - CORNER_WIDTH, middleRect.bottom - ScreenRate, middleRect.left, middleRect.bottom, finderMaskPaint);
		canvas.drawRect(middleRect.left - CORNER_WIDTH, middleRect.bottom,
				middleRect.left + ScreenRate, middleRect.bottom + CORNER_WIDTH, finderMaskPaint);
		canvas.drawRect(middleRect.right, middleRect.bottom - ScreenRate,
				middleRect.right + CORNER_WIDTH, middleRect.bottom, finderMaskPaint);
		canvas.drawRect(middleRect.right - ScreenRate, middleRect.bottom,
				middleRect.right + CORNER_WIDTH, middleRect.bottom + CORNER_WIDTH, finderMaskPaint);

//绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
		slideTop += SPEEN_DISTANCE;
		if (slideTop >= middleRect.bottom) {
			slideTop = middleRect.top;
		}
		canvas.drawBitmap(scanLineBitmap, middleRect.left, slideTop, finderMaskPaint);

		//画扫描框下面的字
		finderMaskPaint.setColor(Color.WHITE);
		finderMaskPaint.setAntiAlias(true);
		finderMaskPaint.setTextSize(DensityUtils.sp2px(context,TEXT_SIZE));
		//paint.setTypeface(Typeface.create("System", Typeface.BOLD));
		String text=getResources().getString(R.string.scan_text);
		float textWidth=(middleRect.right-middleRect.left-finderMaskPaint.measureText(text))/2;

		canvas.drawText(text, middleRect.left+textWidth, (float) (middleRect.bottom + DensityUtils.dp2px(context,40)), finderMaskPaint);//TODO
		//只刷新扫描框的内容，其他地方不刷新
		postInvalidateDelayed(ANIMATION_DELAY, middleRect.left, middleRect.top, middleRect.right, middleRect.bottom);



		//画框
//		zx_code_kuang.setBounds(middleRect);
//		zx_code_kuang.draw(canvas);
//		if (lineRect.bottom < middleRect.bottom) {
//			zx_code_line.setBounds(lineRect);
//			lineRect.top = lineRect.top + lineHeight / 2;
//			lineRect.bottom = lineRect.bottom + lineHeight / 2;
//		} else {
//			lineRect.set(middleRect);
//			lineRect.bottom = lineRect.top + lineHeight;
//			zx_code_line.setBounds(lineRect);
//		}
//		zx_code_line.draw(canvas);
//		postInvalidateDelayed(ANIMATION_DELAY, middleRect.left, middleRect.top, middleRect.right, middleRect.bottom);

	}

	/**
	 * @return
	 */
	public Rect getScanImageRect(Size cameraPreviewSize) {
		Rect reusltRect = null;
		if (measureedWidth < measureedHeight) {
			//图片宽度被拉伸的比例
			float temp = (float) cameraPreviewSize.height / (float) measureedWidth;
			//图片在高度被拉伸的比例
			float tempH = (float) cameraPreviewSize.width / (float) measureedHeight;
			reusltRect = new Rect(middleRect.top, middleRect.left, middleRect.bottom, middleRect.right);
			reusltRect.left = (int) (reusltRect.left * tempH);
			reusltRect.top = (int) (reusltRect.top * temp);
			reusltRect.right = (int) (reusltRect.right * tempH);
			reusltRect.bottom = (int) (reusltRect.bottom * temp);
		} else {
			//图片在宽度被拉伸的比例
			float tempW = (float) cameraPreviewSize.width / (float) measureedWidth;
			//图片高度被拉伸的比例
			float tempH = (float) cameraPreviewSize.height / (float) measureedHeight;
			reusltRect = new Rect(middleRect);
			reusltRect.left = (int) (reusltRect.left * tempW);
			reusltRect.top = (int) (reusltRect.top * tempH);
			reusltRect.right = (int) (reusltRect.right * tempW);
			reusltRect.bottom = (int) (reusltRect.bottom * tempH);
		}
//		int width = measureedWidth * 3 / 4;
//		int height = width;
//		int leftOffset = (measureedWidth - width) / 2;
//		int topOffset = (measureedHeight - height) / 4;
//		reusltRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
		return reusltRect;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureedWidth = MeasureSpec.getSize(widthMeasureSpec);
		measureedHeight = MeasureSpec.getSize(heightMeasureSpec);
		//处理横屏  
//		int borderWidth = (measureedWidth < measureedHeight ? measureedWidth : measureedHeight) / 2;
		int width = measureedWidth * 3 / 4;
		int height = width;
		int leftOffset = (measureedWidth - width) / 2;
		int topOffset = (measureedHeight - height) / 4;
		middleRect.set(leftOffset, topOffset, leftOffset + width , topOffset + height);
//		middleRect.set((measureedWidth - borderWidth) / 2, (measureedHeight - borderWidth) / 2, (measureedWidth - borderWidth) / 2 + borderWidth, (measureedHeight - borderWidth) / 2 + borderWidth);
		lineRect.set(middleRect);
		lineRect.bottom = lineRect.top + lineHeight;

		leftRect.set(0, middleRect.top, middleRect.left, middleRect.bottom);

		topRect.set(0, 0, measureedWidth, middleRect.top);

		rightRect.set(middleRect.right, middleRect.top, measureedWidth, middleRect.bottom);

		bottomRect.set(0, middleRect.bottom, measureedWidth, measureedHeight);
	}
}
