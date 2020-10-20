package com.adam.collection.test.util;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Jerry
 * Generated at: 2019/3/19 11:41
 * WeChat: enGrave93
 * Description: A utility for cache-cleaning and file managing.
 */
@SuppressWarnings("WeakerAccess")
public class FileManager {

    private static final String TAG = FileManager.class.getSimpleName();
    private static final String DEFAULT_PATTERN = "0.00";
    private WeakReference<Context> mContextWR;

    private FileManager() {
    }

    public static FileManager with(Context context) {
        FMH.instance.init(context);
        return FMH.instance;
    }

    public static String format(String pattern, double value) {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern(pattern);
        return format.format(value);
    }

    public static void release() {
        if (FMH.instance.mContextWR != null) {
            FMH.instance.mContextWR.clear();
            FMH.instance.mContextWR = null;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean clearCache(OnClearedCallback callback) {
        Context context = mContextWR.get();
        if (context != null) {
            return deleteFilesByDirectory(context.getExternalCacheDir(), callback);
        }
        return false;
    }

    private void init(Context context) {
        mContextWR = new WeakReference<>(context);
    }

    public boolean deleteFilesByDirectory(File directory, OnClearedCallback callback) {
        int filesCount;
        double filesSize = 0;
        List<File> failedFiles = new ArrayList<>();
        if (directory != null && directory.exists() && directory.isDirectory()) {
            filesCount = directory.listFiles().length;
            int deletedCount = 0;
            for (File child : directory.listFiles()) {
                filesSize += child.length();
                if (child.delete()) {
                    deletedCount++;
                } else {
                    failedFiles.add(child);
                    // Log.e(TAG, "The container for wrapping files that was deleted unsuccessfully is null. No failed files will be returned.");
                }
            }
            if (callback != null) {
                callback.onDelete(failedFiles, format(DEFAULT_PATTERN, filesSize / Math.pow(1024, 2)));
            }
            return deletedCount == filesCount;
        }
        return false;
    }

   public interface OnClearedCallback {
        void onDelete(@NonNull List<File> failedFiles, String cleanedSize);
    }

    private static class FMH {
        private static final FileManager instance = new FileManager();
    }
}
