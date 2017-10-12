package com.shenyu.laikaword.http;

import com.shenyu.laikaword.bean.BaseResponse;
import com.shenyu.laikaword.bean.ReRequest;
import com.shenyu.laikaword.bean.reponse.DidiFuResponse;
import com.shenyu.laikaword.bean.reponse.HeadReponse;
import com.shenyu.laikaword.bean.reponse.UserReponse;
import com.shenyu.laikaword.http.api.NetApi;
import com.shenyu.laikaword.retrofit.callback.FileCallback;
import com.shenyu.laikaword.http.uitls.RetrofitUtils;
import com.shenyu.laikaword.http.uitls.SimpleCallback;
import com.zxj.utilslibrary.utils.LogUtil;

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
    public static void verfacationCodePost(String tel, String pass, SimpleCallback<BaseResponse> observer){
        setSubscribe(service.getVerfcationCodePost(tel, pass),observer);
    }


    //POST请求参数以map传入
    public static void verfacationCodePostMap(Map<String, String> map, SimpleCallback<BaseResponse> observer) {
        setSubscribe(service.getVerfcationCodePostMap(map),observer);
    }

    //Get请求设置缓存
    public static void verfacationCodeGetCache(String tel, String pass,SimpleCallback<BaseResponse> observer) {
        setSubscribe(service.getVerfcationGetCache(tel, pass),observer);
    }

    //Get请求
    public static void verfacationCodeGet(SimpleCallback<DidiFuResponse> observer) {
        setSubscribe(service.getVerfcationGet(),observer);
    }

    //Get请求
    public static void verfacationCodeGetsub(String tel, String pass, Observer<BaseResponse> observer) {
//        setSubscribe(service.getVerfcationGet(tel, pass),observer);
    }

    //Get请求
    public static void Getcache( SimpleCallback<DidiFuResponse> observer) {
        setSubscribe(service.getMainMenu(),observer);
    }

    /**
     * 上传头像
     * @param params
     * @param simpleCallback
     */
    public static  void  uploadMultipleTypeFile(String platform, String sign,
                                                String access_token, String userId, String dataSource,
                                                String version, String accountLinkId, String timestamp,
                                                String devModel, String user_token, String devId
                                                ,Map<String, RequestBody> params,SimpleCallback<HeadReponse> simpleCallback) {
        setSubscribe(service.uploadMultipleTypeFile(platform,sign,access_token,userId,dataSource,version,accountLinkId,timestamp,devModel,user_token,devId,params),simpleCallback);
    }

    /**
     * 下载文件
     * @param fileCallbacks
     */
    public static void downLoadFile(final FileCallback fileCallbacks){
        service.loadFile().enqueue(fileCallbacks);
    }
    /**
     * 插入观察者
     * @param observable
     * @param simpleCallback
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, final SimpleCallback<T> simpleCallback) {
        observable
                .subscribeOn(Schedulers.io())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Observer<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(e.toString());
                    }

                    @Override
                    public void onNext(T t) {

                    }
                });
    }

    /**
     * 登陸
     * @param user
     * @param password
     * @param observe
     */
    public static void loginUser( String user,String password,SimpleCallback<UserReponse> observe){
        setSubscribe(service.loginUser(user,password),observe);
    }

    /**
     * Test
     * @param observe
     */
    public static void test(SimpleCallback<ReRequest> observe){
        setSubscribe(service.test(),observe);
    }

}
