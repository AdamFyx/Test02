package com.adam.collection.test.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.airbnb.lottie.L;

public class MessengerService extends Service {
    private static  final  String TAG="MessengerService";

    private  class MessengerHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG,msg.what+"");
            switch (msg.what){
                case 1:
                        Log.d(TAG, "receive msg from Client:" + msg.getData().getString("msg"));
                        //回应客户端
//                    try {
//                        Log.d("MessengerHandler",msg.replyTo.toString());
//                        Messenger client=msg.replyTo;
//                        Log.d("MessengerHandler",client.toString());
//
//                        Message replyMessage=Message.obtain(null,2);
//                    Bundle bundle=new Bundle();
//                    bundle.putString("reply","嗯，你的消息我已经收到，稍后会回复你的");
//                    replyMessage.setData(bundle);
//
//                        client.send(replyMessage);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    private  final Messenger messenger=new Messenger(new MessengerHandler());
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
