package com.shenyu.laikaword.http.uitls.Interceptor;

import android.util.Log;

import com.zxj.utilslibrary.utils.LogUtil;

import java.io.IOException;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
