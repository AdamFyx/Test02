package com.adam.collection.test.scan;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera.Size;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ZxingTools {
	private static Hashtable<DecodeHintType, Object> decodeConfig = new Hashtable<DecodeHintType, Object>();
	static {
		List<BarcodeFormat> allFormats = new ArrayList<BarcodeFormat>();
		allFormats.add(BarcodeFormat.CODABAR);
		allFormats.add(BarcodeFormat.CODE_39);
		allFormats.add(BarcodeFormat.CODE_93);
		allFormats.add(BarcodeFormat.CODE_128);
		allFormats.add(BarcodeFormat.DATA_MATRIX);
		allFormats.add(BarcodeFormat.EAN_8);
		allFormats.add(BarcodeFormat.EAN_13);
		allFormats.add(BarcodeFormat.ITF);
		allFormats.add(BarcodeFormat.QR_CODE);
		allFormats.add(BarcodeFormat.RSS_14);
		allFormats.add(BarcodeFormat.EAN_13);
		allFormats.add(BarcodeFormat.RSS_EXPANDED);
		allFormats.add(BarcodeFormat.UPC_A);
		allFormats.add(BarcodeFormat.UPC_E);
		decodeConfig.put(DecodeHintType.POSSIBLE_FORMATS, allFormats);
	}

	/**
	 * 解析DecodeData
	 * @param decodeData
	 * @param roate90
	 * @return
	 */
	public static Result decodeDecodeData(com.adam.collection.test.scan.DecodeData decodeData, boolean roate90) {
		Bitmap barCodeBitMap = getBarCodeBitMap(decodeData, roate90);
		Rect previewRect = new Rect(0, 0, barCodeBitMap.getWidth(), barCodeBitMap.getHeight());
		return decodeBitmap(barCodeBitMap, previewRect);
	}

	/**
	 * 从PlanarYUVLuminanceSource获取Bitmap
	 * @param source
	 * @param roate90
	 * @return
	 */
	public static Bitmap getBarCodeBitMap(PlanarYUVLuminanceSource source, boolean roate90) {
		int[] pixels = source.renderThumbnail();
		int width = source.getThumbnailWidth();
		int height = source.getThumbnailHeight();
		Bitmap sourceBitmap = Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_4444);
		if (roate90) {
			return roate90Bitmap(sourceBitmap);
		} else {
			return sourceBitmap;
		}
	}

	/**
	 * 根据DecodeData获取Bitmap
	 * @param decodeData
	 * @param roate90
	 * @return
	 */
	public static Bitmap getBarCodeBitMap(DecodeData decodeData, boolean roate90) {
		byte[] data = decodeData.getData();
		Size size = decodeData.getSourceSize();
		Rect preRect = decodeData.getPreRect();
		PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data, size.width, size.height, preRect.left, preRect.top, preRect.width(), preRect.height(), false);
		return getBarCodeBitMap(source, roate90);
	}

	/**
	 * 将Bitmap旋转90
	 * @param sourceBitmap
	 * @return
	 */
	public static Bitmap roate90Bitmap(Bitmap sourceBitmap) {
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.setRotate(90);
		Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
		if (!sourceBitmap.isRecycled())
			sourceBitmap.recycle();
		return resultBitmap;
	}

	/**
	 * 解析PlanarYUVData
//	 * @param data
//	 * @param width
//	 * @param height
	 * @param previewRect
	 * @param roate90
	 * @return
	 */
	public static Result decodePlanarYUVData(byte[] yuvData, Size size, Rect previewRect, boolean roate90) {
		if (roate90) {
			yuvData = roate90YUVdata(yuvData, size);
		}
		PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(yuvData, size.width, size.height, previewRect.left, previewRect.top, previewRect.width(), previewRect.height(), false);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
		return decodeBinaryBitmap(binaryBitmap);
	}

	/**
	 * 将yuvData旋转90度
	 * @param yuvData
	 * @param size
	 * @return
	 */
	public static byte[] roate90YUVdata(byte[] yuvData, Size size) {
		byte[] rotatedData = new byte[yuvData.length];
		for (int y = 0; y < size.height; y++) {
			for (int x = 0; x < size.width; x++)
				rotatedData[x * size.height + size.height - y - 1] = yuvData[x + y * size.width];
		}
		int tmp = size.width;
		size.width = size.height;
		size.height = tmp;
		return rotatedData;
	}

	/**
	 * 解析Bitmap
	 * @param bitmap
	 * @param previewRect
//	 * @param needResultBitmap
	 * @return
	 */
	private static Result decodeBitmap(Bitmap bitmap, Rect previewRect) {
		int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getPixels(pixels, 0, bitmap.getWidth(), previewRect.left, previewRect.top, previewRect.right, previewRect.bottom);
		RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
		return decodeBinaryBitmap(binaryBitmap);
	}

	/**
	 * 解析BinaryBitmap
	 * @param binaryBitmap
	 * @return
	 */
	private static Result decodeBinaryBitmap(BinaryBitmap binaryBitmap) {
		MultiFormatReader multiFormatReader = new MultiFormatReader();
		multiFormatReader.setHints(decodeConfig);
		Result decode = null;
		try {
			decode = multiFormatReader.decode(binaryBitmap);
		} catch (NotFoundException e) {
		} finally {
			multiFormatReader.reset();
		}
		return decode;
	}

	//////////////////////////////////////////////////////
	////////////////////    生成                /////////////////////
	//////////////////////////////////////////////////////
	/**
	 * 生成二维码
	 * @param content
	 * @param needWidth
	 * @param needHeight
	 * @return
	 * @throws Exception
	 */
	public static Bitmap encodeQr(String content, int needWidth, int needHeight) throws Exception {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, needWidth, needHeight);
		return convertBitMatrix2BitMap(bitMatrix);
	}

	/**
	 * 生成一维码（128）
	 * @param content
	 * @param needWidth
	 * @param needHeight
	 * @return
	 * @throws Exception
	 */
	public static Bitmap encodeBarcode(String content, int needWidth, int needHeight) throws Exception {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.CODE_128, needWidth, needHeight);
		return convertBitMatrix2BitMap(bitMatrix);
	}

	/**
	 * 将BitMatrix转化为Bitmap
	 * @param bitMatrix
	 * @return
	 */
	private static Bitmap convertBitMatrix2BitMap(BitMatrix bitMatrix) {
		int bitmapWidth = bitMatrix.getWidth();
		int bitmapHeight = bitMatrix.getHeight();
		int[] pixels = new int[bitmapWidth * bitmapHeight];
		for (int x = 0; x < bitmapWidth; x++) {
			for (int y = 0; y < bitmapHeight; y++) {
				if (bitMatrix.get(x, y)) {
					pixels[y * bitmapWidth + x] = 0xff000000; // black pixel
				} else {
					pixels[y * bitmapWidth + x] = 0xffffffff; // white pixel
				}
			}
		}
		Bitmap createBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_4444);
		createBitmap.setPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);
		return createBitmap;
	}
}
