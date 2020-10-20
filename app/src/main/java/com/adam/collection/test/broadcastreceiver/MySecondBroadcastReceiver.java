package com.adam.collection.test.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/12 15:26
 * <br/>
 *
 * @since
 */
public  class MySecondBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"我是自定义的第二个广播",Toast.LENGTH_LONG).show();
        abortBroadcast();
    }
}
