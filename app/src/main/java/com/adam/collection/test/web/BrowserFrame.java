//package com.adam.collection.test.web;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.os.Build;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.ci123.babyweekend.home.WelfareFragment;
//import com.ci123.babyweekend.util.SPUtils;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;
//
///**
// * Author: Jerry
// * Generated at: 2019/8/7 15:31
// * WeChat: enGrave93
// * Description:
// */
//public class BrowserFrame extends TBSFrame {
//
//    private static final String TAG = BrowserFrame.class.getSimpleName();
//
//    private static final String[] VIABLE_HOSTS = {WelfareFragment.class.getSimpleName()};
//
//    private String host;
//    private BFCallback callback;
//
//    public BrowserFrame(@NonNull Context context) {
//        super(context);
//    }
//
//    public BrowserFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public BrowserFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    protected WebViewClient getDescendantWebViewClient() {
//        return new BrowserFrameWebViewClient();
//    }
//
//    @Override
//    protected TBSWebChromeClient getDescendantWebChromeClient() {
//        return new BrowserFrameWebChromeClient();
//    }
//
//    @SuppressLint("SetJavaScriptEnabled")
//    @Override
//    protected void initNecessaryParams(WebView webView) {
//        Context context = webView.getContext();
//        WebSettings webSettings = webView.getSettings();
//        String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
//        webSettings.setUserAgent("babyweekend " + SPUtils.getString(webView.getContext(), "save_agent", "android"));
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setDefaultTextEncodingName("UTF-8");
//        webSettings.setUseWideViewPort(true);
//        webSettings.setDatabaseEnabled(true);
//        webSettings.setDatabasePath(dir);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSettings.setGeolocationEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
//        }
//    }
//
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    private boolean meetOverrideUrlLoadingRequirements() {
//        if (TextUtils.isEmpty(host)) {
//            Log.e(TAG, "Empty host.");
//            return false;
//        }
//        for (String s : VIABLE_HOSTS) {
//            if (s.equals(host)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void setCallback(BFCallback callback) {
//        this.callback = callback;
//    }
//
//    public interface BFCallback {
//        void onPreOverrideUrlLoading(String url);
//    }
//
//    private class BrowserFrameWebChromeClient extends TBSWebChromeClient {
//
//    }
//
//    private class BrowserFrameWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
//            if (meetOverrideUrlLoadingRequirements()) {
//                if (callback != null) {
//                    callback.onPreOverrideUrlLoading(s);
//                }
//                return true;
//            } else {
//                return super.shouldOverrideUrlLoading(webView, s);
//            }
//        }
//    }
//
//}
