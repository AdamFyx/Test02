package com.adam.collection.test.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Author: Adam Inc.
 * <br/>
 * Created at: 2020/11/9 16:56
 * <br/>
 *
 * @since
 */
public class ClipeBoardUtil {
    /**
     * 获取剪切板里内容
     * @param context
     * @return
     */
    public static String getClipeBoardContent(Context context){
        ClipboardManager clipboardManager= (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData primaryClip = clipboardManager.getPrimaryClip();
        String content=null;
        if (primaryClip!=null&&primaryClip.getItemCount()>0){
            ClipData.Item itemAt = primaryClip.getItemAt(0);
            if (itemAt!=null){
                content=itemAt.getText().toString();
            }
        }
        return content;
    }
    /**
     * 放置内容发到剪切板
     */
    public static void setClipeBoardContent(Context context,String content){
        ClipboardManager clipboardManager= (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData primaryClip = ClipData.newPlainText("Label",content);//纯文本内容
        clipboardManager.setPrimaryClip(primaryClip);
    }
    /**
     * 清空剪切板
     */
    public static void clear(Context context){
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                manager.setPrimaryClip(manager.getPrimaryClip());
                manager.setPrimaryClip(ClipData.newPlainText("",""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}