package com.adam.collection.test.ui;

import com.adam.collection.test.util.ConstantsUtils;
import com.adam.collection.test.util.DeviceUtils;
import com.adam.collection.test.util.SPUtils;
import com.bumptech.glide.Glide;
import com.adam.collection.test.base.BaseActivity;
import com.adam.collection.test.contract.MainContract;
import com.adam.collection.test.locationUtils.LocationCallBack;
import com.adam.collection.test.locationUtils.LocationUtil;
import com.adam.collection.test.presenter.MainPresenter;
import com.adam.collection.test.R;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.mmkv.MMKV;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.D;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener , LocationCallBack {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
//  public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private PushAgent mPushAgent;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1000;
    private static final int REQUEST_CODE_PERMISSION_GROUP=3000;
    private  SharedPreferences sharedPreferences;

    private  String TAG="MainActivity";
    TextView tAddress;
    TextView qrCode;
    ImageView imageView;
    TextView deviceInfo;
    EditText editText;
    TextView dateInfo;
    @Override
    protected void initPresenter() {
        mPresenter=new MainPresenter();
    }

    @Override
    protected void initView() {

        mPresenter.testGetMpresenter();
        mPresenter.testDb();
        mPresenter.testRequestNetwork();
        mPresenter.testPreference();
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.location).setOnClickListener(this);
        findViewById(R.id.scan).setOnClickListener(this);
        findViewById(R.id.wxlogin).setOnClickListener(this);
        findViewById(R.id.clipboard).setOnClickListener(this);
        findViewById(R.id.uploadImage).setOnClickListener(this);
        findViewById(R.id.openwechat).setOnClickListener(this);
        findViewById(R.id.getDeviceInfo).setOnClickListener(this);
        findViewById(R.id.saveData).setOnClickListener(this);
        findViewById(R.id.getData).setOnClickListener(this);
        findViewById(R.id.mmkv).setOnClickListener(this);
        findViewById(R.id.test).setOnClickListener(this);
        //requestPermission();
         tAddress=findViewById(R.id.address);
        qrCode=findViewById(R.id.qrcode);
        mPushAgent=PushAgent.getInstance(MainActivity.this);
        imageView=findViewById(R.id.glideImage);
        deviceInfo=findViewById(R.id.deviceInfo);
        editText=findViewById(R.id.dateEdit);
        dateInfo=findViewById(R.id.dateInfo);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "你自己的UMENG_APPKEY");
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("推送服务注册成功","返回的deviceToken是"+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d("推送服务注册失败","返回的错误信息是"+s);
            }
        });
        init();
        //这个name是储存的xml文件的名称
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);

        //初始化mmkv
            String rootDir= MMKV.initialize(this);
            Log.d("mmkvroot",rootDir);
        //把sharePerences的数据复制过来，之后可以直接获取到 sharedPreferences里面的数据
        MMKV preferences=MMKV.defaultMMKV();
        preferences.importFromSharedPreferences(sharedPreferences);

    }
    private void init() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

        LoadingGlideImage();

    }

    public  void LoadingGlideImage(){
        String url="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600335101151&di=a48e3907f1b3555f79bdf8ad1a5db22e&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F36%2F48%2F19300001357258133412489354717.jpg";
        Glide.with(MainActivity.this)
                .load(url)
                .into(imageView);
    }
    //此回调回一直调用位置信息
//    private class MyAMapLocationListener implements AMapLocationListener {
//
//        @Override
//        public void onLocationChanged(AMapLocation aMapLocation) {
//            if (aMapLocation != null) {
//                if (aMapLocation.getErrorCode() == 0) {
//                    Log.e("位置：", aMapLocation.getAddress());
//                } else {
//                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                    Log.e("AmapError11", "location Error, ErrCode:"
//                            + aMapLocation.getErrorCode() + ", errInfo:"
//                            + aMapLocation.getErrorInfo());
//                }
//            }
//        }
//    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                Thread thread1=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.asynget();
                    }
                });
                thread1.start();
                break;
            case R.id.btn2:
                Thread thread2=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.synget();
                    }
                });
                thread2.start();
                break;
            case R.id.btn3:
                Thread thread3=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.postRequestString();
                    }
                });
                thread3.start();
                break;
            case R.id.btn4:
                Thread thread4=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.postRequestForm();
                    }
                });
                thread4.start();
                break;
            case  R.id.location:
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){//未开启定位权限
                    //开启定位权限,200是标识码
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
                }else{
                    //开始定位
                    Toast.makeText(MainActivity.this,"已开启定位权限",Toast.LENGTH_LONG).show();
                }
                //高德定位
                //连续位置信息
//                MapLocationHelper helper = new MapLocationHelper(this, this);
//                helper.startMapLocation();
                LocationUtil.startLocation(this, new LocationUtil.OnLocationChangedListener() {
                    @Override
                    public void onLocationChanged(String lon, String lat, String address) {
                        Log.i("获得定位：","lon="+lon+"   lat="+lat+"  address="+address);
                        tAddress.setText("获得定位："+"lon="+lon+"   lat="+lat+"  address="+address);
                    }
                });
                break;
            case  R.id.scan:
//去寻找是否已经有了相机的权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

                    //Toast.makeText(MainActivity.this,"您申请了动态权限",Toast.LENGTH_SHORT).show();
                    //如果有了相机的权限有调用相机
                    Intent intent = new Intent(this, ScanActivity.class);
                    this.startActivityForResult(intent, 1);
                    this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_null);
                }else{
                    //否则去请求相机权限
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},100);

                }
                break;
            case R.id.wxlogin:
                Intent intent=new Intent();
                intent.setClass(this, MainWXActivity.class);
                startActivity(intent);
                break;
            case  R.id.clipboard:
                ClipboardManager clipboardManager= (ClipboardManager) MainActivity.this.getSystemService(MainActivity.this.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText(null,"我是谁，我在哪");
                clipboardManager.setPrimaryClip(clipData);
                break;
            case R.id.uploadImage:
//                if (resultCode == RESULT_OK) {
//                    switch (requestCode) {
//                        case PictureConfig.CHOOSE_REQUEST:
//                            // 结果回调
//                            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                            break;
//                        default:
//                            break;
//                    }
                break;
            case R.id.openwechat:
                Intent intent1 = new Intent();
                ComponentName cmp=new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                intent1.setAction(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_LAUNCHER);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setComponent(cmp);
                MainActivity.this.startActivity(intent1);
                break;
            case R.id.getDeviceInfo:
                 String deviceModel= getDeviceModel();
                 String udid= UUID.randomUUID().toString();
                Log.d("udid",udid);
                deviceInfo.setText(deviceModel+"---->"+udid);
                break;
            case R.id.saveData:
                String str= editText.getText().toString();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("content2",str).commit();
                break;
            case R.id.getData:
                String content=sharedPreferences.getString("content2","");
                dateInfo.setText(content);
                break;
            case R.id.mmkv:
                getSomeValue();
                break;
            case R.id.test:
                timeCompare("1600425756");
                break;
                }


    }
    public static void timeCompare(String startTime1) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

             Log.d("date1",dateFormat.format(new Date(Long.valueOf(startTime1+"000"))));

    }
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间


    public void getSomeValue(){
        MMKV kv=MMKV.defaultMMKV();
        kv.encode("bool",true);
                kv.encode("int",Integer.MIN_VALUE);
                kv.encode("string","hello from mmkv");
     //   Log.d("我是数据",kv.decodeString("bool").toString());
     //   Log.d("我是数据",kv.decodeString("int").toString());
        Log.d("我是数据",kv.decodeString("string").toString());
        Log.d("我是数据",kv.decodeString("content").toString());


    }
    public static String getDeviceModel(){
        String phoneModel="";
        try {
            phoneModel = URLEncoder.encode(android.os.Build.MODEL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return phoneModel;
    }
    //扫一扫调用返回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        qrCode.setText(data.getStringExtra("result"));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void testGetMview() {
        Log.d("print","我是v层的引用");
    }
    /*
    获取定位权限
     */
    //定位的地址详情
    @Override
    public void onCallLocationSuc(AMapLocation location) {
        Log.d(TAG, "Address：" + location.getAddress());
        Log.d(TAG, "City：" + location.getCity());
        Log.d(TAG, "CityCode：" + location.getCityCode());
        Log.d(TAG, "Country：" + location.getCountry());
        Log.d(TAG, "Province：" + location.getProvince());
        Log.d(TAG, "Latitude：" + location.getLatitude());
        Log.d(TAG, "Longitude：" + location.getLongitude());
    }


}