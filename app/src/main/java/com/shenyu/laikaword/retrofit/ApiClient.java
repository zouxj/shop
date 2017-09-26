package com.shenyu.laikaword.retrofit;

import com.shenyu.laikaword.common.UrlConstant;
import com.shenyu.laikaword.http.uitls.CommonParamntercepter;
import com.shenyu.laikaword.http.uitls.Interceptor.LogInterceptor;

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

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new LogInterceptor());
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
                builder.addInterceptor(new CommonParamntercepter());

            OkHttpClient okHttpClient = builder.build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(UrlConstant.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)

                    .build();

        }

        return mRetrofit;

    }
}
