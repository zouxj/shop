package com.shenyu.laikaword.retrofit;

import com.shenyu.laikaword.common.UrlConstant;
import com.shenyu.laikaword.http.uitls.CommonParamntercepter;
import com.shenyu.laikaword.http.uitls.Interceptor.LogInterceptor;
import com.shenyu.laikaword.http.uitls.OkHttp3Utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public class ApiClient {


    public static Retrofit mRetrofit;
    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(UrlConstant.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(OkHttp3Utils.getmOkHttpClient())
                    .build();

        }

        return mRetrofit;

    }
}
