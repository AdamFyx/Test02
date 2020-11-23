package com.adam.collection.test.listseekbar;

public class SeekBarBean implements Comparable<SeekBarBean> {
    /**
     * 是否在线
     */
    boolean isOnline;
    /**
     * 是否获得焦点
     */
    boolean isACTION_DOWN;
    /**
     * 刻度值时间字符串转化为刻度值数组的下标
     */
    int time;
    /**
     * 图标id
     */
    int iconId;

    public SeekBarBean( boolean isOnline, int time,int iconId) {

        this.isOnline = isOnline;
        this.isACTION_DOWN=false;
        this.time = time;
        this.iconId=iconId;
    }

    public boolean isACTION_DOWN() {
        return isACTION_DOWN;
    }

    public void setACTION_DOWN(boolean ACTION_DOWN) {
        isACTION_DOWN = ACTION_DOWN;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        if (time<=0){
            this.time = 0;
        }else if (time>=17){
            this.time=17;
        }else {
            this.time = time;
        }
    }


    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }


    @Override
    public int compareTo(SeekBarBean o) {
        int result=this.getTime()-o.getTime();
        return result;
    }
}
