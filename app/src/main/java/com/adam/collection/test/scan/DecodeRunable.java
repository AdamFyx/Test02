package com.adam.collection.test.scan;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.adam.collection.test.ui.ScanActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.lang.ref.SoftReference;

/**
 * 解析线程
 * @author Administrator
 */
public class DecodeRunable implements Runnable {
	public static final String TAG = "DecodeRunable";
	public SoftReference<com.adam.collection.test.scan.WeakHandler<ScanActivity>> handlerReference;
	public com.adam.collection.test.scan.DecodeData decodeData;
	public boolean roate90;

	public DecodeRunable(com.adam.collection.test.scan.WeakHandler<ScanActivity> handler, DecodeData decodeData, boolean roate90) {
		handlerReference = new SoftReference<com.adam.collection.test.scan.WeakHandler<ScanActivity>>(handler);
		this.decodeData = decodeData;
		this.roate90 = roate90;
	}

	@Override
	public void run() {
		Result result = ZxingTools.decodeDecodeData(decodeData, roate90);
		if (null != result) {
			WeakHandler<ScanActivity> weakHandler = handlerReference.get();
			if (null != weakHandler) {
				ScanActivity qrFinderActivity = weakHandler.activiceReference.get();
				if (null == qrFinderActivity || qrFinderActivity.isReciveReuslt()) {
					return;
				}
				//通知扫描页面已经扫描到结果了
				qrFinderActivity.setReciveReuslt(true);
				Message obtainMessage = weakHandler.obtainMessage(0);
				BarcodeFormat barcodeFormat = result.getBarcodeFormat();
				String text = result.getText();
				Bundle data = new Bundle();
				data.putSerializable("BarcodeFormat", barcodeFormat);
				data.putString("text", text);
				obtainMessage.setData(data);
				obtainMessage.sendToTarget();
				Log.d(TAG, "BarcodeFormat:" + barcodeFormat.toString() + "     内容:" + text);
			}
		}
	}
}
