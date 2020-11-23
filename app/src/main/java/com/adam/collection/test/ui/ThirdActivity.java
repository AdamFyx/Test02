package com.adam.collection.test.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.adam.collection.test.R;
import com.adam.collection.test.adapter.Fruit;
import com.adam.collection.test.adapter.FruitAdapter;
import com.adam.collection.test.base.BaseActivity;
import com.adam.collection.test.contract.MainContract;
import com.adam.collection.test.presenter.MainPresenter;
import com.adam.collection.test.util.ActivityController;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThirdActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener {

    private static final String TAG = "ThirdActivity";
    private List<Fruit> fruitList = new ArrayList<Fruit>();
    private  LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter intentFilter;

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter();
    }
    private TextView textView;
    @Override
    protected void initView() {
        ActivityController.addActivity(this);
        findViewById(R.id.nextpage3).setOnClickListener(this);
        findViewById(R.id.sendLocalBroast).setOnClickListener(this);
        findViewById(R.id.saveFile).setOnClickListener(this);
        findViewById(R.id.loadFile).setOnClickListener(this);
        textView =findViewById(R.id.showdata);
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_test);
        //瀑布
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //横向 修改宽度为100dp
        //LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
            //本地广播获取实例
        localBroadcastManager=LocalBroadcastManager.getInstance(this);

        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.MY_BROADCAST");
        localReceiver=new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册本地广播监听器
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextpage3:
                Intent intent=new Intent(ThirdActivity.this,FourActivity.class);
                startActivity(intent);
                Log.i(TAG, "onClick: ");
                break;
            case R.id.sendLocalBroast:
                Intent intent1=new Intent("com.example.broadcasttest.MY_BROADCAST");
                localBroadcastManager.sendBroadcast(intent1);
                break;
            case R.id.saveFile:
                    String str="我是储存在文件里面的数据";
                    save(str);
                break;
            case R.id.loadFile:
                String text=load();
                if(!TextUtils.isEmpty(text)){
                    textView.setText(text);
                }
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.third_activity;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    public void testGetMview() {
        Log.d("print", "我是v层的引用");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit(getRandomLengthName("Apple"), R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit(getRandomLengthName("Banana"), R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit(getRandomLengthName("Orange"), R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit(getRandomLengthName("Watermelon"), R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit(getRandomLengthName("Pear"), R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit(getRandomLengthName("Grape"), R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit(getRandomLengthName("Pineapple"), R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit(getRandomLengthName("Strawberry"), R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit(getRandomLengthName("Cherry"), R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit(getRandomLengthName("Mango"), R.drawable.mango_pic);
            fruitList.add(mango);
        }
    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1; i++) {
            builder.append(name);
        }
        return builder.toString();
    }

        class  LocalReceiver extends BroadcastReceiver{

            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,"我是本地的广播",Toast.LENGTH_LONG).show();
            }
        }
        public void save(String Text){
            FileOutputStream out=null;
            BufferedWriter writer=null;
            try {
                out=openFileOutput("data2",Context.MODE_PRIVATE);
                writer=new BufferedWriter(new OutputStreamWriter(out));
                writer.write(Text);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                    try {
                        if(writer!=null) {
                            writer.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            public String  load(){
                FileInputStream in=null;
                BufferedReader reader=null;
                StringBuilder content=new StringBuilder();
                try {
                    in=openFileInput("data");
                    reader=new BufferedReader(new InputStreamReader(in));
                    String line="";
                    while((line=reader.readLine())!=null){
                        content.append(line);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return  content.toString();
            }

}