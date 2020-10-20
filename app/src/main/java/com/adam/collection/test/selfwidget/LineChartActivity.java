package com.adam.collection.test.selfwidget;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adam.collection.test.R;
import com.adam.collection.test.util.LogUtils;
import com.adam.collection.test.views.LineChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class LineChartActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    int num = 1024;
    private LineChart mLineChart;
    private String[] mMonthStrs = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月",};
    private SeekBar sbOne;
    private SeekBar sbTwo;
    private SeekBar sbThree;
    private SeekBar sbFour;
    private SeekBar sbFive;
    private int month;
    private ArrayList<Float> sumData;
    private List<String> seletcMonths;
    private TextView tvFive;
    private TextView tvFour;
    private TextView tvThree;
    private TextView tvOne;
    private TextView tvTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        initViews();
        initData();
    }

    private void initViews() {
        mLineChart = (LineChart) findViewById(R.id.line_chart);
        findViewById(R.id.start).setOnClickListener(this);

        tvOne = (TextView) findViewById(R.id.tv_one);
        sbOne = (SeekBar) findViewById(R.id.sb_one);
        sbOne.setOnSeekBarChangeListener(this);

        tvTwo = (TextView) findViewById(R.id.tv_two);
        sbTwo = (SeekBar) findViewById(R.id.sb_two);
        sbTwo.setOnSeekBarChangeListener(this);

        tvThree = (TextView) findViewById(R.id.tv_three);
        sbThree = (SeekBar) findViewById(R.id.sb_three);
        sbThree.setOnSeekBarChangeListener(this);

        tvFour = (TextView) findViewById(R.id.tv_four);
        sbFour = (SeekBar) findViewById(R.id.sb_four);
        sbFour.setOnSeekBarChangeListener(this);

        tvFive = (TextView) findViewById(R.id.tv_five);
        sbFive = (SeekBar) findViewById(R.id.sb_five);
        sbFive.setOnSeekBarChangeListener(this);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    mLineChart.setSumData(sumData, seletcMonths);
                    break;
            }
        }
    };
    private void dynamic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                num = (int) (num / 1.1);
                sumData.set(0, (float) num);
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }

        }).start();

    }

    private void initData() {
        sumData = new ArrayList<>();
        sumData.add((float) sbOne.getProgress());
        sumData.add((float) sbTwo.getProgress());
        sumData.add((float) sbThree.getProgress());
        sumData.add((float) sbFour.getProgress());
        sumData.add((float) sbFive.getProgress());
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        seletcMonths = Arrays.asList("", "", "", "", "");
        for (int i = 0; i < 5; i++) {
            if (month < 1) {
                month = 12;
            }
            seletcMonths.set(4 - i, mMonthStrs[month - 1]);
            month--;
        }
        mLineChart.setSumData(sumData, seletcMonths);
        tvOne.setText(seletcMonths.get(0));
        tvTwo.setText(seletcMonths.get(1));
        tvThree.setText(seletcMonths.get(2));
        tvFour.setText(seletcMonths.get(3));
        tvFive.setText(seletcMonths.get(4));
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float values = (float) (seekBar.getProgress());
        switch (seekBar.getId()) {
            case R.id.sb_one:
                sumData.set(0, values);

                LogUtils.d("onProgressChanged", sumData.toString());
                LogUtils.d("onProgressChanged", seletcMonths.toString());
                mLineChart.setSumData(sumData, seletcMonths);
                break;
            case R.id.sb_two:
                sumData.set(1, values);
                mLineChart.setSumData(sumData, seletcMonths);
                break;
            case R.id.sb_three:
                sumData.set(2, values);
                mLineChart.setSumData(sumData, seletcMonths);
                break;
            case R.id.sb_four:
                sumData.set(3, values);
                mLineChart.setSumData(sumData, seletcMonths);
                break;
            case R.id.sb_five:
                sumData.set(4, values);
                mLineChart.setSumData(sumData, seletcMonths);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                LogUtils.d("onClick", "我被点到了");
                dynamic();
                break;
        }
    }
}
