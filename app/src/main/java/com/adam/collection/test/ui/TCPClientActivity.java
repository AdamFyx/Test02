package com.adam.collection.test.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adam.collection.test.R;
import com.adam.collection.test.service.TCPServerService;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPClientActivity extends AppCompatActivity implements View.OnClickListener {
    private  static  final int MESSAGE_RECEIVE_NEW_MSG=1;
    private  static final int MESSAGE_SOCKET_CONNECTED=2;
    private Button mSendButton;
    private TextView mMessageTextView;
    private EditText mMessageEditText;
    private PrintWriter mPrintWriter;
    private Socket mClientScoket;

    @SuppressWarnings("HandlerLeak")
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MESSAGE_RECEIVE_NEW_MSG:{
                    mMessageTextView.setText(mMessageTextView.getText()+(String)msg.obj);
                    break;
                }
                case MESSAGE_SOCKET_CONNECTED:{
                    mSendButton.setEnabled(true);
                    break;
                }
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_c_p_client);
        mMessageTextView=findViewById(R.id.msg_container);
        mSendButton=findViewById(R.id.send);
        mSendButton.setOnClickListener( this);
        mMessageEditText=findViewById(R.id.msg);
        Intent service=new Intent(this, TCPServerService.class);
        startService(service);
        new Thread(){
            @Override
            public void run() {
                connectTCPServer();
                super.run();
            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        if(mClientScoket!=null){
            try {
                mClientScoket.shutdownInput();
                mClientScoket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if(view==mSendButton){
            final  String msg=mMessageEditText.getText().toString();
            if(!TextUtils.isEmpty(msg)&&mPrintWriter!=null){
                mPrintWriter.println(msg);
                mMessageEditText.setText("");
                String time=formatDateTime(System.currentTimeMillis());
                final  String showedMsg="self"+time+":"+msg+"\n";
                mMessageTextView.setText(mMessageTextView.getText()+showedMsg);
            }
        }
    }
    @SuppressLint("SimpleDateFormayt")
    private String formatDateTime(Long time){
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }
    private void connectTCPServer(){
        Socket socke=null;
        while (socke==null){
            try {
                socke=new Socket("localhost",8688);
//                socke=new Socket("http://www.baidu.com",13);
                mClientScoket=socke;
                mPrintWriter=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socke.getOutputStream())),true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("connect server success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                System.out.println("connect tcp server failed,retry..");
                e.printStackTrace();
            }
        }
        //接受服务器端的消息
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(socke.getInputStream()));
            while (!TCPClientActivity.this.isFinishing()){
                String msg=br.readLine();
                System.out.println("receive:"+msg);
                if(msg!=null){
                    String time=formatDateTime(System.currentTimeMillis());
                    final  String showedMsg="serciexe"+time+":"+msg+"\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showedMsg).sendToTarget();
                }
            }
            System.out.println("quit..");
            mPrintWriter.close();
            br.close();
            socke.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}