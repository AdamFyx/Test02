package com.adam.collection.test.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static android.os.ParcelFileDescriptor.MODE_APPEND;

/**
 * Author: Adam Inc.
 * <br/>
 * Created at: 2020/11/12 10:40
 * Bitmap和Byte之间的相互转化
 * <br/>
 *
 * @since
 */
public class BitmapHelper {
    public static  byte[] BitmapToByte(Bitmap bitmap) {
        // 第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        // 第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
       return byteArray;
    }
    /**
     * 从SharedPreferences中取出Bitmap，显示在ImageView中
     */
    public static Bitmap ByteToBitmap(byte[] byteArray) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                byteArray);
        // 第三步:利用ByteArrayInputStream生成Bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        return bitmap;
    }


}