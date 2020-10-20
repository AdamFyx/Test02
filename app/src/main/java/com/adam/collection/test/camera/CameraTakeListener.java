package com.adam.collection.test.camera;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/30 13:35
 * <br/>
 *
 * @since
 */
public interface CameraTakeListener {
    void  onSuccess(File bitmapFile, Bitmap mBitmap);

    void  onFail(String error);
}
