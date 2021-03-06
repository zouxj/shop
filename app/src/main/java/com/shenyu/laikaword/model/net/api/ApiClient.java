package com.shenyu.laikaword.model.net.api;

import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.net.okhttp.OkHttp3Utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public class ApiClient {


    public static Retrofit mRetrofit;
    public  static Retrofit retrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(OkHttp3Utils.getmOkHttpClient())
                    .build();

        }
//        mRetrofit = new Retrofit.Builder()
//                .baseUrl(Constants.API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(OkHttp3Utils.getmOkHttpClient())
//                .build();
        return mRetrofit;

    }
}
