package com.adam.collection.test.ui;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.adam.collection.test.R;
import com.adam.collection.test.base.BaseActivity;
import com.adam.collection.test.camera.CameraTakeListener;
import com.adam.collection.test.camera.CameraTakeManager;
import com.adam.collection.test.camera.LogUtil;
import com.adam.collection.test.contract.MainContract;
import com.adam.collection.test.presenter.MainPresenter;
import com.adam.collection.test.presenter.SecondPresenter;
import com.adam.collection.test.refresh.CustomRefreshHeader;
import com.adam.collection.test.util.ActivityController;
import com.adam.collection.test.util.LogUtils;
import com.adam.collection.test.util.UserManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.ClosedFileSystemException;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.permission.EasyPermissions;

public class SecondActivity extends BaseActivity<SecondPresenter> implements MainContract.View, View.OnClickListener {

    TextView coordinate;
    TextView scoordinate;
    LinearLayout lview;
    ScrollView scrollView;
    private SmartRefreshLayout refreshLayout;
    ArrayAdapter<String> adapter;
    List<String> contactsList=new ArrayList<>();
    ListView linksView;
    private static final String TAG = "SecondActivity";
    ImageView giftImage;

    public  static  final  int TAKE_PHOTO=1;
    private  ImageView picture;
    private Uri imageUri;
    private  TextView phoneLocationResult;
    @Override
    protected void initPresenter() {
        mPresenter = new SecondPresenter();
    }

    @Override
    protected void initView() {
        ActivityController.addActivity(this);
        refreshLayout = findViewById(R.id.scrollView);
        findViewById(R.id.uppage).setOnClickListener(this);
        findViewById(R.id.nextpage).setOnClickListener(this);
        findViewById(R.id.sendbrocast).setOnClickListener(this);
        findViewById(R.id.sendNotification).setOnClickListener(this);
        findViewById(R.id.takephoto).setOnClickListener(this);
        findViewById(R.id.phoneLocation).setOnClickListener(this);
        findViewById(R.id.handler).setOnClickListener(this);
        picture=findViewById(R.id.picture);
        linksView=findViewById(R.id.linksView);

        phoneLocationResult=findViewById(R.id.phoneLocationResult);
        giftImage=findViewById(R.id.giftest);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtils.d("SecondActivity","我被刷新了");
                    refreshLayout.finishRefresh();//结束刷新
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtils.d("SecondActivity","我是加载更多");
                    refreshLayout.finishLoadMore();
                    String s="aa";
            }
        });


        coordinate = findViewById(R.id.coordinate);
        scoordinate = findViewById(R.id.scoordinate);
        lview = findViewById(R.id.view);
        /*
        空白区域操作
         */
        lview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                VelocityTracker velocityTracker=VelocityTracker.obtain();
                velocityTracker.addMovement(motionEvent);
                velocityTracker.computeCurrentVelocity(1000);
                int xVelocity=(int) velocityTracker.getXVelocity();
                int yVelocity=(int) velocityTracker.getXVelocity();
                LogUtils.d("我是滑动速度",xVelocity+"-->"+yVelocity);
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        coordinate.setText("当前view起始位置：" + motionEvent.getX() + "-->" + motionEvent.getY());
                        scoordinate.setText("当前手机屏幕起始位置：" + motionEvent.getRawX() + "-->" + motionEvent.getRawY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        coordinate.setText("当前view移动中的坐标:" + motionEvent.getX() + "-->" + motionEvent.getY());
                        scoordinate.setText("当前手机屏幕起始位置：" + motionEvent.getRawX() + "-->" + motionEvent.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        coordinate.setText("当前view最后的位置:" + motionEvent.getX() + "-->" + motionEvent.getY());
                        coordinate.setText("当前手机屏幕最后的位置:" + motionEvent.getRawX() + "-->" + motionEvent.getRawY());
                        velocityTracker.clear();
                        velocityTracker.recycle();
                        break;
                }
                return true;
            }
        });
        /*
        动态图的滑动
         */

        /*
        如果你在屏幕上滑动距离小于这个偏移量 则默认不算你在滑动
         */
        LogUtils.d("我是滑动的最小偏移量", ViewConfiguration.get(this).getScaledTouchSlop() + "");
//        init()
        //初始化联系人列表
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
        linksView.setAdapter(adapter);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            readContacts();
        }
        LogUtils.d(TAG,"我执行到dao l ");
        LogUtils.d(TAG, UserManager.sUserId+"");
       /*
          //手机温度检测 目前只在Pixels设备受支持
        */
        PowerManager manager= (PowerManager) getSystemService(Context.POWER_SERVICE);
        //监听手机热值状态
        manager.addThermalStatusListener(new PowerManager.OnThermalStatusChangedListener() {
            @Override
            public void onThermalStatusChanged(int i) {
                LogUtils.d("我是手机热值状态--》"+i);
            }
        });

    }
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    phoneLocationResult.setText("位置2");
                    break;
            }
        }
    };
//菜单栏的初始化
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
//菜单栏的操作事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(SecondActivity.this,"我是增加",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(SecondActivity.this,"我是删除",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.uppage:
                Intent intent=new Intent();
                intent.putExtra("msg2","我是点击传过来的数据");
                setResult(181,intent);
                finish();
                break;
            case R.id.nextpage:
                Intent intent1=new Intent();
                intent1.setAction("third");
                startActivity(intent1);
                break;
            case R.id.sendbrocast:
                Intent intent2=new Intent("com.example.broadcasttest.MY_BROADCAST");
                intent2.addFlags(0x01000000);
                sendOrderedBroadcast(intent2,null);
                break;
            case R.id.sendNotification:
                Intent intent3=new Intent(this,ThirdActivity.class);
//                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent3,0);

                NotificationManagerCompat manager= NotificationManagerCompat.from(this);
                String channelId=creatrNotificationChannel("my_channel_ID","my_channel_NAME",NotificationManager.IMPORTANCE_HIGH);
                androidx.core.app.NotificationCompat.Builder  notification = new  androidx.core.app.NotificationCompat.Builder(this,channelId)
                        .setContentTitle("这是标题")
                        .setContentText("这是内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{0,1000,1000,1000})
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("阿达阿斯顿阿斯顿阿德iouh " +
                                "阿斯顿阿斯顿阿斯顿阿斯顿阿三阿斯顿哥特搞同给他人"))
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory
//                        .decodeResource(getResources(),R.drawable.strawberry_pic)))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true);

                manager.notify(1,notification.build());
                break;
            case R.id.takephoto:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    //创建File对象，用于存储拍照后的图片
                    File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                    try {
                        if(outputImage.exists()){
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(Build.VERSION.SDK_INT>=24){
                        imageUri= FileProvider.getUriForFile(this,"com.adam.collection.test.fileprovider",outputImage);

                    }else{
                        imageUri=Uri.fromFile(outputImage);
                    }
                    //启动相机程序
                    Intent intent4=new Intent("android.media.action.IMAGE_CAPTURE");
                    intent4.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent4,TAKE_PHOTO);
                }else{
                    //否则去请求相机权限
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},100);
                }
                break;
            case R.id.phoneLocation:
                getLocation();
                break;
            case R.id.handler:
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       Message message=new Message();
                       message.what=1;
                       handler.sendMessage(message);
                   }
               }).start();
                break;

        }

    }

        //入口是getLocation
        /**
         * 定位：权限判断
         */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {
        //检查定位权限
        ArrayList<String> permissions = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        //判断
        if (permissions.size() == 0) {//有权限，直接获取定位
            getLocationLL();
        } else {//没有权限，获取定位权限
            requestPermissions(permissions.toArray(new String[permissions.size()]), 2);

            LogUtils.d("*************", "没有定位权限");
        }
    }

    /**
     * 定位：获取经纬度
     */
    private void getLocationLL() {
        LogUtils.d("*************", "获取定位权限1 - 开始");
        Location location = getLastKnownLocation();
        if (location != null) {

            //日志
            String locationStr = "维度：" + location.getLatitude() + "\n"
                    + "经度：" + location.getLongitude();
            LogUtils.d("*************", "经纬度：" + locationStr);
            phoneLocationResult.setText(locationStr);
        } else {
            Toast.makeText(this, "位置信息获取失败", Toast.LENGTH_SHORT).show();
            LogUtils.d("*************", "获取定位权限7 - " + "位置获取失败");
        }
    }
    //意外关闭时保存数据
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 定位：得到位置对象
     * @return
     */
    private Location getLastKnownLocation() {
        //获取地理位置管理器
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LogUtils.d(TAG, "onActivityResult: "+requestCode);
        switch (requestCode){
            //拍照返回完成照片的加载。
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        LogUtils.d(TAG, "onActivityResult:bitmap "+bitmap.toString());
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //通知的创建渠道
    private  String creatrNotificationChannel(String channelID,String channelNAME,int level){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel=new NotificationChannel(channelID,channelNAME,level);
            manager.createNotificationChannel(channel);
            return channelID;
        }else{
            return null;
        }
    }
    //读取手机联系人
    private  void readContacts(){
        Cursor cursor=null;
        cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                //获取联系人姓名
                String displayName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //获取联系人手机号
                String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsList.add(displayName+"\n"+number);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode){
           case 1:
               if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   readContacts();
               }else
               {
                   Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
               }
               break;
               /*
               定位权限申请到之后返回；
                */
           case 2://定位
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   LogUtils.d("*************", "同意定位权限");
                   getLocationLL();
               } else {
                   Toast.makeText(this, "未同意获取定位权限", Toast.LENGTH_SHORT).show();
               }
               break;
           default:
       }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.second_activity;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("msg","我是SecondActivity中的数据");
        setResult(180,intent);
        LogUtils.d("SecondActivity",intent.getStringExtra("msg"));
        finish();
        super.onBackPressed();

    }

    @Override
    public void testGetMview() {
        LogUtils.d("print", "我是v层的引用");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}