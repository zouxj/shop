package com.shenyu.laikaword.module.shop.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.js.AndroidtoJs;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;

import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品详情
 */
public class ShopDateilActivity extends LKWordBaseActivity {

    @BindView(R.id.wb_load)
    WebView wbLoad;

    @Override
    public int bindLayout() {
        return R.layout.activity_shop_dateil;
    }

    @Override
    public void initView() {
        setToolBarTitle("商品详情");
        WebSettings webSettings = wbLoad.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        String webURL = "http://t.shop.comingcard.com/view/detail/detail.html?goodsId=1";
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        wbLoad.loadUrl(webURL);
        wbLoad.setWebViewClient(new WebViewClient() {
                                      @Override
                                      public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                          // 步骤2：根据协议的参数，判断是否是所需要的url
                                          // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                                          //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                                          Uri uri = Uri.parse(url);
                                          // 如果url的协议 = 预先约定的 js 协议app://goOrder
                                          // 就解析往下解析参数
                                          if ( uri.getScheme().equals("app")) {
                                              // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                                              // 所以拦截url,下面JS开始调用Android需要的方法
                                              if (uri.getAuthority().equals("goOrder")) {
                                                  //  步骤3：
                                                  // 执行JS所需要调用的逻辑
                                                  IntentLauncher.with(ShopDateilActivity.this).launch(ConfirmOrderActivity.class);
//                                                 ToastUtil.showToastShort("js调用了Android的方法");
//                                                  // 可以在协议上带有参数并传递到Android上
//                                                  HashMap<String, String> params = new HashMap<>();
//                                                  Set<String> collection = uri.getQueryParameterNames();
                                              }

                                              return true;
                                          }
                                          return super.shouldOverrideUrlLoading(view, url);
                                      }
                                  }
        );

    }


    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }

}