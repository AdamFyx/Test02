package com.adam.collection.test.util;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/11 10:18
 * <br/>
 *
 * @since
 */

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import com.cchao.voicesplayer.library.VoiceSpeaker;
import com.cchao.voicesplayer.library.VoiceSynthesize;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 友盟推送类
 */
public class UmengHelper {

    public static final String TAG = "UmengHelper";
    public static UmengHelper mUmengHelper;
    public static String dToken = "";
    private PushAgent mPushAgent;
    private String mfromString;
        private Context mcontext;
    public static UmengHelper getInstance() {
        if (mUmengHelper == null) {
            mUmengHelper = new UmengHelper();
        }
        return mUmengHelper;
    }
    public static String getDeviceToken(){
        return  dToken;
    }
    VoiceSpeaker mVoiceSpeaker;

    public void init(final Context context, final String fromString) {
        mfromString=fromString;
        mcontext=context;
        mVoiceSpeaker= VoiceSpeaker.getInstance(context);
        //context = getApplicationContext();
        //友盟推送初始化
        UMConfigure.init(context, "5f9f74982065791705fed403", "baidu", UMConfigure.DEVICE_TYPE_PHONE, "eb3de8512d56fa4041cb6ec2cc1834c2");
        //统计
        //UMConfigure.setLogEnabled(true);//设置组件化的Log开关
        // 选用AUTO页面采集模式，如果是在AUTO页面采集模式下，则需要注意，所有Activity中都不能调用MobclickAgent.onResume和onPause方法
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        PushAgent mPushAgent = PushAgent.getInstance(context);
        //不调用这个的话无法接收推送
        mPushAgent.onAppStart();
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("UmengHelper推送服务注册成功"+mfromString, "返回的deviceToken是" + deviceToken);
                dToken=deviceToken;
            }

            @Override
            public void onFailure(String s, String s1) {

                Log.e("UmengHelper推送服务注册失败"+mfromString, "返回的错误信息是" + s);
            }

        });

//        getAppInfo();
////        setUserTag();
////        setUserAlias();
        setMessageHandler();
//        dealWithNotificationMessage();
//        setNotificationClickHandler();
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        //可以通过调用如下方法控制SDK运行调试日志是否输出，默认情况下SDK运行调试日志关闭。需要用户手动打开。
        //App正式上线前请关闭SDK运行调试日志。避免无关Log输出。
        UMConfigure.setLogEnabled(false);

    }
    public void setMessageHandler() {
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(context).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(context).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            //处理推送过来的进入数据
            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                super.dealWithNotificationMessage(context, uMessage);
                Log.d("UmengHelper所接受到的数据"+mfromString, uMessage.text);

                    mVoiceSpeaker.setPlayRatio(0.88f);
                    mVoiceSpeaker.setMinMaxPlayEnd(100, 1500);
                    mVoiceSpeaker.putQueue(new VoiceSynthesize()
                        .prefix("success")
                        .numString(uMessage.text)
                        .build());

            }
        };
        PushAgent.getInstance(mcontext).setMessageHandler(messageHandler);

    }
    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean timeCompare(String time) {
        boolean flag = false;
        //注意：传过来的时间格式必须要和这里填入的时间格式相同
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = dateFormat.parse(time);//开始时间//开始时间
            Date date2 = new Date(System.currentTimeMillis() - 180000);
            Log.d("date1",date1.toString());
            Log.d("date2",date2.toString());
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime() < date1.getTime()) {
                //结束时间小于开始时间
                flag = true;
            } else if (date2.getTime() > date1.getTime()) {
                //结束时间大于开始时间
                flag = false;
            }
        } catch (Exception e) {
        }
        Log.d("flagxxx",flag+"");
        return flag;
    }
    /**
     * 判断字符串是否是金额
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        java.util.regex.Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }
        else{
            return true;
        }
    }
}


