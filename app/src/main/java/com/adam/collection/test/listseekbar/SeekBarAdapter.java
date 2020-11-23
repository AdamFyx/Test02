package com.adam.collection.test.listseekbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.adam.collection.test.R;

import java.util.ArrayList;
import java.util.Collections;

public class SeekBarAdapter extends BaseAdapter implements CustomSeekBar.SeekBarListener {
    static String[] strings=new String[]{"立即","1s","2s","3s","4s","5s","6s","7s","8s","9s","10s","20s","30s","1m","2m","3m","4m","5m"};
    java.util.List<SeekBarBean> seekBarBeanList;
    LayoutInflater layoutInflater;
    Context context;

    public SeekBarAdapter(java.util.List<Bean> list, Context context) {
        this.seekBarBeanList = detailList(list);
        Collections.sort(seekBarBeanList);
        this.context = context;
    }

    public java.util.List<Bean> getList(){
        java.util.List<Bean> list=new ArrayList<>();
        SeekBarBean seekBarBean;
        for (int i=0;i<seekBarBeanList.size();i++){
            seekBarBean=seekBarBeanList.get(i);
            list.add(new Bean(seekBarBean.getIconId(),seekBarBean.isOnline(),strings[seekBarBean.getTime()]));
        }
        return list;
    }

    private int  stringToTime(@androidx.annotation.NonNull String string){
        int result=0;
        int length=strings.length;
        for (int i=0;i<length;i++){
            if (string.equals(strings[i])){
                result=i;
                break;
            }
        }
        return result;
    }

    /**
     * @param list:接口类型 的集合
     * @return List<SeekBarBean>:接口类型处理后的集合
     */
    private java.util.List<SeekBarBean> detailList(java.util.List<Bean> list){
        java.util.List<SeekBarBean> seekBarBeanList=new ArrayList<>();
            Bean bean;
            int time;
            for (int i=0;i<list.size();i++){
                bean=list.get(i);
                time=stringToTime(bean.getTime());
                seekBarBeanList.add(new SeekBarBean(bean.isOnline(),time,bean.getResId()));
            }
            return seekBarBeanList;
    }

    @Override
    public int getCount() {
        return seekBarBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
     * 注意这里不能用convertView存储view，否则notifyDataSetChanged()会受到影响。
     */
    @Override
    public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
         layoutInflater=LayoutInflater.from(context);
         ViewHolder viewHolder;
         viewHolder=new ViewHolder();
         convertView=layoutInflater.inflate(R.layout.item_seekbar,parent,false);
         viewHolder.customSeekBar=convertView.findViewById(R.id.csb_test);
         viewHolder.customSeekBar.setSeekBarListener(this);
        viewHolder.customSeekBar.setSeekBarBean(seekBarBeanList.get(position));
        return convertView;
    }

    @Override
    public void beforeActionUpFinish() {
        Collections.sort(seekBarBeanList);
       notifyDataSetChanged();
    }



    static class ViewHolder {
        CustomSeekBar customSeekBar;
    }



}
