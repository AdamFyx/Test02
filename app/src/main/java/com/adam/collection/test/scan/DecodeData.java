package com.adam.collection.test.scan;

import android.graphics.Rect;
import android.hardware.Camera.Size;

/**
 * 自定义解析数据包
 * @author Administrator
 */
public class DecodeData {
	private byte[] data;
	private Size sourceSize;
	private Rect preRect;

	public DecodeData(byte[] data, Size sourceSize, Rect preRect) {
		this.data = data;
		this.sourceSize = sourceSize;
		this.preRect = preRect;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Size getSourceSize() {
		return sourceSize;
	}

	public void setSourceSize(Size sourceSize) {
		this.sourceSize = sourceSize;
	}

	public Rect getPreRect() {
		return preRect;
	}

	public void setPreRect(Rect preRect) {
		this.preRect = preRect;
	}

}
