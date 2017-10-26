package com.shenyu.laikaword.model.net.Intercepter;

import com.shenyu.laikaword.model.net.downloadmanager.FileResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by shenyu_zxjCode on 2017/10/11 0011.
 */

public   class DownIntercepter implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse
                .newBuilder()
                .body(new FileResponseBody(originalResponse.body()))
                .build();
    }
}