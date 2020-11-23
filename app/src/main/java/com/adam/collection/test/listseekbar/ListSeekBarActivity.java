package com.adam.collection.test.listseekbar;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.adam.collection.test.R;


import java.util.ArrayList;

public class ListSeekBarActivity extends Activity {
    SeekBarListView seekBarListView;
    SeekBarAdapter seekBarAdapter;
    java.util.List<Bean> list=new ArrayList<>();
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listseekbar);
        list.add(new Bean(R.drawable.communication_select,false,"立即"));
       list.add(new Bean(R.drawable.communication_select,true,"1m"));
       list.add(new Bean(R.drawable.communication_select,true,"30s"));
        list.add(new Bean(R.drawable.communication_select,true,"1m"));
        list.add(new Bean(R.drawable.communication_select,true,"2m"));
        list.add(new Bean(R.drawable.communication_select,true,"2m"));
        list.add(new Bean(R.drawable.communication_select,true,"1m"));
        list.add(new Bean(R.drawable.communication_select,true,"5m"));
        list.add(new Bean(R.drawable.communication_select,true,"1m"));
        list.add(new Bean(R.drawable.communication_select,false,"10s"));
        list.add(new Bean(R.drawable.communication_select,false,"立即"));
        list.add(new Bean(R.drawable.communication_select,false,"7s"));
        list.add(new Bean(R.drawable.communication_select,false,"5s"));

        //第一步获取控件
        seekBarListView=findViewById(R.id.bbb);
        //第二部实例化适配器
        seekBarAdapter=new SeekBarAdapter(list,this);
        //为自定义控件设置适配器(整体情况listview 配置的步骤一样)
        seekBarListView.setAdapter(seekBarAdapter);

    }
}