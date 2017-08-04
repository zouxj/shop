package com.shenyu.laikaword.http;

import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.reponse.DidiFuResponse;
import com.shenyu.laikaword.bean.reponse.HeadReponse;
import com.shenyu.laikaword.http.api.NetApi;
import com.shenyu.laikaword.http.uitls.RetrofitUtils;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/8/3 0003.
 * 网络请求的操作类
 */

public class NetWorks extends RetrofitUtils {

    protected static final NetApi service = getmRetrofit().create(NetApi.class);
    //POST请求
    public static void verfacationCodePost(String tel, String pass, Observer<BaseReponse> observer){
        setSubscribe(service.getVerfcationCodePost(tel, pass),observer);
    }


    //POST请求参数以map传入
    public static void verfacationCodePostMap(Map<String, String> map, Observer<BaseReponse> observer) {
        setSubscribe(service.getVerfcationCodePostMap(map),observer);
    }

    //Get请求设置缓存
    public static void verfacationCodeGetCache(String tel, String pass,Observer<BaseReponse> observer) {
        setSubscribe(service.getVerfcationGetCache(tel, pass),observer);
    }

    //Get请求
    public static void verfacationCodeGet(Observer<DidiFuResponse> observer) {
        setSubscribe(service.getVerfcationGet(),observer);
    }

    //Get请求
    public static void verfacationCodeGetsub(String tel, String pass, Observer<BaseReponse> observer) {
//        setSubscribe(service.getVerfcationGet(tel, pass),observer);
    }

    //Get请求
    public static void Getcache( Observer<DidiFuResponse> observer) {
        setSubscribe(service.getMainMenu(),observer);
    }

    /**
     * 上传头像
     * @param des
     * @param params
     * @param observe
     */
    public static  void  uploadMultipleTypeFile(Map<String, String> paramsText, Map<String, RequestBody> params,Observer<HeadReponse> observe) {
        setSubscribe(service.uploadMultipleTypeFile(paramsText,params),observe);
    }
    /**
     * 插入观察者
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

}
