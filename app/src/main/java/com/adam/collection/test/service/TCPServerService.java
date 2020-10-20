package com.adam.collection.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/14 15:55
 * <br/>
 *
 * @since
 */
public class TCPServerService extends Service {
    private  boolean mIsServiceDestoryed=false;
    private  String[] mDefinedMessages=new String[]{
            "你好啊，哈哈",
            "请问你叫什么名字呀？",
            "今天南京天气不错啊，shy",
            "你知道嘛？我可是可以和多个人同时聊天的哦",
            "给你讲哥笑话吧"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed=true;
        super.onDestroy();
    }

    private class TcpServer implements  Runnable{
        @SuppressWarnings("resource")
        @Override
        public void run() {
            ServerSocket serverSocket=null;
            try {
                serverSocket=new ServerSocket(8688);
            } catch (IOException e) {
                System.err.print("estavlish tcp server failed,port:8688");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestoryed){
                //接受客户端请求
                try {
                    final Socket client=serverSocket.accept();
                    System.out.print("accept");
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            super.run();
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    private  void responseClient(Socket client) throws IOException{
        //用于接受客户端消息
        BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
        out.println("欢迎来到聊天室");
        while (!mIsServiceDestoryed){
            String str=in.readLine();
            System.out.print("msg from clicent:"+str);
            if(str==null){
                //客户端断开连接
                break;
            }
            int i=new Random().nextInt(mDefinedMessages.length);
            String msg=mDefinedMessages[i];
            out.println(msg);
            System.out.println("send:"+msg);
        }
        System.out.println("client quit");
        //关闭流
        out.close();
        in.close();
        client.close();
    }
}
