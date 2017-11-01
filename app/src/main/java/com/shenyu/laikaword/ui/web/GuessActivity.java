package com.shenyu.laikaword.ui.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.module.goods.order.ui.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.module.login.ui.activity.LoginActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import butterknife.BindView;

public class GuessActivity extends LKWordBaseActivity {
    @BindView(R.id.wb_load)
    WebView wbLoad;
    String webURL;
    @Override
    public int bindLayout() {
        return R.layout.activity_guess;
    }

    @Override
    public void initView() {
        initWebView();
         webURL = getIntent().getStringExtra("weburl");
        wbLoad.loadUrl(webURL);

    }

    @SuppressLint("NewApi")
   private void initWebView(){
       WebSettings webSettings = wbLoad.getSettings();
        webSettings.setUserAgentString("laikashopapp");
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setLoadWithOverviewMode(true);
//       webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//       webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //
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

}
    @SuppressLint("WrongConstant")
    @Override
    public void doBusiness(Context context) {
        wbLoad.requestFocus();
        wbLoad.setScrollBarStyle(0);
        wbLoad.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
//                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议app://goOrder
                // 就解析往下解析参数
                LogUtil.i("webView",url);
               String aliPay="https://qr.alipay.com";
               String qqPay = "https://myun.tenpay.com/mqq/pay/qrcode";
                if (url.contains(aliPay)||url.contains(qqPay)){
                    IntentLauncher.with(GuessActivity.this).launchViews(url);
                    return true;
                }

//                }
                return super.shouldOverrideUrlLoading(view, url);

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
