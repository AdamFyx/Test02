package com.adam.collection.test.bean;

import android.graphics.Bitmap;

/**
 * Author: Adam Inc.
 * <br/>
 * Created at: 2020/11/19 15:13
 * <br/>
 *
 * @since
 */
public  class ImageBean {
    private int id;
    private Bitmap bitmap;

    public ImageBean(int id, Bitmap bitmap) {
        this.id = id;
        this.bitmap = bitmap;
    }

    public ImageBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
