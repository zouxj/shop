package com.shenyu.laikaword.model.net.Intercepter;

import com.zxj.utilslibrary.utils.LogUtil;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by shenyu_zxjCode on 2017/9/20 0020.
 */

public class LogInterceptor implements HttpLoggingInterceptor.Logger  {
    @Override
    public void log(String message) {
        LogUtil.i("HttpLogInfo", message);
    }

}
