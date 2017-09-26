package com.shenyu.laikaword.http.uitls;

import com.google.gson.GsonBuilder;
import com.shenyu.laikaword.bean.NoBodyEntity;
import com.shenyu.laikaword.common.UrlConstant;
import com.shenyu.laikaword.http.uitls.OkHttp3Utils;

import java.security.NoSuchAlgorithmException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class RetrofitUtils {
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkhttpClient;

    /**
     * 获取Retrofit
     * @return
     */
    protected static  Retrofit getmRetrofit(){
        if (null==mRetrofit){
            if (null==mOkhttpClient){
                    mOkhttpClient = OkHttp3Utils.getmOkHttpClient();
            }
            //Retrofit2后使用build设计模式
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(UrlConstant.HOST+"/")   //设置服务器路径
                    .addConverterFactory(new NobodyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())) //添加转化库，默认是Gson
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //添加回调库，采用RxJava
                    .client(mOkhttpClient)  //设置使用okhttp网络请求
                    .build();
        }
        return  mRetrofit;
    }
}
