package com.adam.collection.test.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adam.collection.test.R;
import com.adam.collection.test.base.BaseActivity;
import com.adam.collection.test.contract.MainContract;
import com.adam.collection.test.presenter.MainPresenter;
import com.adam.collection.test.selfwidget.LineChartActivity;
import com.adam.collection.test.selfwidget.MyWatchViewActivity;
import com.adam.collection.test.selfwidget.ViewPagerIndicatorActivity;
import com.adam.collection.test.util.ActivityController;
import com.umeng.commonsdk.debug.I;

public class FourActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener {



    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter();
    }

    @Override
    protected void initView() {
    ActivityController.addActivity(this);
    findViewById(R.id.finish).setOnClickListener(this);
    findViewById(R.id.linechart).setOnClickListener(this);
    findViewById(R.id.watch).setOnClickListener(this);
    findViewById(R.id.viewpager).setOnClickListener(this);
    findViewById(R.id.fragment).setOnClickListener(this);
    String []datas={"张三","李四","王五","苹果","香蕉","栗子","西瓜","冬瓜",};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(FourActivity.this,android.R.layout.simple_list_item_1,datas);
        ListView listView=findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish:
                ActivityController.finishAll();
                break;
            case R.id.linechart:
                Intent intent=new Intent(this, LineChartActivity.class);
                startActivity(intent);
                break;
            case R.id.watch:
                startActivity(new Intent(this, MyWatchViewActivity.class));
                break;
            case R.id.viewpager:
                startActivity(new Intent(this, ViewPagerIndicatorActivity.class));
                break;
            case R.id.fragment:
                startActivity(new Intent(this,FragmentActivity.class));
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.four_activity;
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
    }
}