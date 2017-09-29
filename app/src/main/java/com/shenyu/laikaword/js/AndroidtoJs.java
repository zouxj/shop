package com.shenyu.laikaword.js;

import android.webkit.JavascriptInterface;

import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/28 0028.
 */

public class AndroidtoJs extends  Object  {
    @JavascriptInterface
    public void hello(String msg) {
        ToastUtil.showToastShort("js 调用了Andoid的方法");
    }

}
