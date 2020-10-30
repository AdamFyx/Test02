package com.adam.collection.test.bean;

/**
 * Author: Adam Inc.
 * <br/>
 * Created at: 2020/10/27 13:33
 * <br/>
 *
 * @since
 */
//轮播图对应的实体类
public class InfoBean {
    private  int picture;

    public InfoBean(int picture) {
        this.picture = picture;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
