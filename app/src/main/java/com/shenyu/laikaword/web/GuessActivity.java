package com.shenyu.laikaword.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;

import butterknife.BindView;

public class GuessActivity extends LKWordBaseActivity {
    @BindView(R.id.wb_load)
    WebView wbLoad;

    @Override
    public int bindLayout() {
        return R.layout.activity_guess;
    }

    @Override
    public void initView() {
        initWebView();
        String webURL = getIntent().getStringExtra("weburl");
        wbLoad.loadUrl(webURL);

    }

    @SuppressLint("NewApi")
   private void initWebView(){
       WebSettings webSettings = wbLoad.getSettings();
       webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
       webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
   }
    @Override
    public void doBusiness(Context context) {

        wbLoad.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

    }

    @Override
    public void setupActivityComponent() {

    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        wbLoad.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        super.onPause();
        wbLoad.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wbLoad != null) {
            wbLoad.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wbLoad.clearHistory();

            ((ViewGroup) wbLoad.getParent()).removeView(wbLoad);
            wbLoad.destroy();
            wbLoad = null;
        }
    }
    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wbLoad.canGoBack()) {
            wbLoad.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
