package com.adam.collection.test.web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.adam.collection.test.R;
import com.adam.collection.test.ui.MainActivity;
import com.adam.collection.test.util.ClipeBoardUtil;
import com.adam.collection.test.util.MarqueeText;

public class ZtbWebview extends AppCompatActivity {
    String pasteString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ztb_webview);
        Intent intent=getIntent();
        pasteString=intent.getStringExtra("url");
        //获得控件
        WebView webView = (WebView) findViewById(R.id.webview);
        //访问网页
        webView.loadUrl(pasteString);
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
    }
    /*
    获取粘贴板中的数据
     */
//    private String getClipboardData() {
//        this.getWindow().getDecorView().post(new Runnable() {
//            @Override
//            public void run() {
//                //把获取到的内容打印出来
//                Log.i("YoungerHu", ClipeBoardUtil.getClipeBoardContent(ZtbWebview.this));
//                pasteString=ClipeBoardUtil.getClipeBoardContent(ZtbWebview.this);
//                Log.i("YoungerHu", pasteString);
//
//            }
//        });
//            return pasteString;
//    }
}