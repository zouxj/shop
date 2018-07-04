package com.shenyu.laikaword.model.web;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.ui.view.widget.ProgressWebView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class
GuessActivity extends LKWordBaseActivity {
    @BindView(R.id.wb_load)
    ProgressWebView wbLoad;
    String webURL;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    public int bindLayout() {
        return R.layout.activity_guess;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView() {
        initWebView();
        webURL = getIntent().getStringExtra("weburl");
        wbLoad.loadUrl(webURL);
        mToolbarSubTitle.setBackground(UIUtil.getDrawable(R.mipmap.refresh_icon));
        mToolbarSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wbLoad.reload();
            }
        });


    }


    @SuppressLint("NewApi")
    private void initWebView() {
        wbLoad.getSettings().setTextZoom(100);

        WebSettings webSettings = wbLoad.getSettings();
        webSettings.setUserAgentString(webSettings.getUserAgentString()+";laikashopapp");


        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
       webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//       webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        // enable navigator.geolocation    
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        // enable Web Storage: localStorage, sessionStorage   
        webSettings.setDomStorageEnabled(true);
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void doBusiness(Context context) {
        wbLoad.requestFocus();
        wbLoad.setScrollBarStyle(0);
        wbLoad.setInterReceivedTitle(new ProgressWebView.InterReceivedTitle() {
            @Override
            public void setTitile(String titile) {
                toolbarTitle.setText(titile);
            }
        });
        wbLoad.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
//                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议app://goOrder
                // 就解析往下解析参数
//                LogUtil.i("webView", url);

                ShopMainReponse shopMainReponse= (ShopMainReponse) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
                for (String ux:shopMainReponse.getPayload().getExternalURLs()){
                    if (url.contains(ux)) {
                        Intent mIntent =new Intent();
                        mIntent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(url);
                        mIntent.setData(content_url);
                        startActivity(mIntent);
                        return true;
                    }
                }





//                }
                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
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
//        wbLoad.pauseTimers();
//        MobclickAgent.onPageStart("GuessActivity");
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        super.onPause();
        wbLoad.onPause();
//        wbLoad.resumeTimers();
//        MobclickAgent.onPageEnd("GuessActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wbLoad != null) {
            wbLoad.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wbLoad.clearHistory();
            ((ViewGroup) wbLoad.getParent()).removeView(wbLoad);
            wbLoad.destroy();
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

    @OnClick(R.id.toolbar_left)
    public void onViewClicked() {
        if(wbLoad.canGoBack()){
            wbLoad.goBack(); // goBack()表示返回WebV
        }else {
            finish();
        }
    }

}
