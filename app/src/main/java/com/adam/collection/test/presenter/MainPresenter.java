package com.adam.collection.test.presenter;

import android.util.Log;


import com.adam.collection.test.base.BaesPresenter;
import com.adam.collection.test.contract.MainContract;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/9/2 17:17
 * <br/>
 *
 * @since
 */
 public  class MainPresenter extends BaesPresenter<MainContract.View> implements MainContract.Presenter{
  public  String TAG="MainPresenter";
    @Override
    public void testGetMpresenter() {
        Log.d("print","我是p层的引用");
        mView.testGetMview();
    }

    @Override
    public void testDb() {
        mDataManager.testDb();
    }

    @Override
    public void testRequestNetwork() {
        mDataManager.testRequestNetwork();
    }

    @Override
    public void testPreference() {
        mDataManager.testPreference();
    }
    //异步get请求
    public void asynget(){
       String url="http://www.baidu.com";
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
        Response response= null;
            response = okHttpClient.newCall(request).execute();
            String responseData=response.body().string();
            Log.d(TAG,"我是数据"+responseData.toString());
            Call call=okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG,"OnFailure:");
                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Log.d(TAG,"OnResponse:"+response.body().string());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //同步get请求
    public void synget(){
        String url="http://www.baidu.com";
        OkHttpClient okHttpClient=new OkHttpClient();
        final  Request request=new Request.Builder().url(url).build();
        final  Call call=okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response= null;
                try {
                    response = call.execute();
                    Log.d(TAG,"run:"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //post请求
    public void postRequestString(){
        String url="http://www.baidu.com";
        OkHttpClient okHttpClient=new OkHttpClient();
        MediaType mediaType=MediaType.parse("text/x-markdown;charset=utf-8");
        RequestBody requestBody=RequestBody.create(mediaType,"RequestBody");
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"onFailture:");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            Log.d(TAG,"onResponse:"+response.body().string());
            }
        });
    }
    //post表单请求
    public  void  postRequestForm(){
        String username="admin";
        String password="pwd";
        String url="http://www.baidu.com";
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"onFailure:");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG,"onResponse:"+response.body().string());
            }
        });
    }
}
