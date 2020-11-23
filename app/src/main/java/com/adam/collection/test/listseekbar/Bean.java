package com.adam.collection.test.listseekbar;

public class Bean {
    int resId;
    boolean isOnline;
    String time;

    public Bean(int resId, boolean isOnline, String time) {
        this.resId = resId;
        this.isOnline = isOnline;
        this.time = time;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
