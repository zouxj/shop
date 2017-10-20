package com.shenyu.laikaword.http.api;

import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.ReRequest;
import com.shenyu.laikaword.bean.reponse.HeadReponse;
import com.shenyu.laikaword.bean.reponse.UserReponse;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface NetApi {


    //设缓存有效期为1天
     static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
     static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
     static final String CACHE_CONTROL_NETWORK = "max-age=0";
    //POST请求
    @FormUrlEncoded
    @POST("bjws/app.user/login")
    Observable<BaseReponse> getVerfcationCodePost(@Field("tel") String tel, @Field("password") String pass);

    //POST请求
    @FormUrlEncoded
    @POST("bjws/app.user/login")
    Observable<BaseReponse> getVerfcationCodePostMap(@FieldMap Map<String, String> map);

    //GET请求
    @GET("router?v=1.0.0&app_key=48e5e13229b82c1b4e6e8c96151f0637&sessio" +
            "n=034b3a932677461b40aeb646131b4a7c&method=glsx.ddcb.mobile.getCommonDeviceList&tim" +
            "estamp=2017-08-01+11%3A05%3A39&softVersion=5.7.1.1&channel=android&format=json&sign=ef11655d85265ae6dab6d1d0d014aabe")
    Observable<BaseReponse> getVerfcationGet();


    //GET请求，设置缓存
    @Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
    @GET("bjws/app.user/login")
    Observable<BaseReponse> getVerfcationGetCache(@Query("tel") String tel, @Query("password") String pass);


    @Headers("Cache-Control: public," + CACHE_CONTROL_NETWORK)
    @GET("router?v=1.0.0&app_key=48e5e13229b82c1b4e6e8c96151f0637&sessio" +
            "n=034b3a932677461b40aeb646131b4a7c&method=glsx.ddcb.mobile.getCommonDeviceList&tim" +
            "estamp=2017-08-01+11%3A05%3A39&softVersion=5.7.1.1&channel=android&format=json&sign=ef11655d85265ae6dab6d1d0d014aabe")
    Observable<BaseReponse> getMainMenu();

    @Multipart
    @POST("appapi/v5_3/image/upload/userHeadPic")
    Observable<HeadReponse> uploadMultipleTypeFile
    (
            @Query("platform") String platform,
            @Query("sign") String sign,
            @Query("access_token") String access_token,
            @Query("userId") String userId,
            @Query("dataSource") String dataSource,
            @Query("version") String version,
            @Query("accountLinkId") String accountLinkId,
            @Query("timestamp") String timestamp,
            @Query("devModel") String devModel,
            @Query("user_token") String user_token,
            @Query("devId") String devId,
            @PartMap   Map<String,RequestBody> paramText);

    /**
     * 文件下载
     * @return
     */
    @GET("temp/apk.apk")
    Call<ResponseBody> loadFile();

    /**
     * 用户登录
     * @param
     * @return
     */
    @POST("appapi/login")
    Observable<UserReponse> loginUser(@Query("usename") String usename,@Query("password")String password);
//测试接口
    @GET("MApi/test/test")
    Observable<ReRequest> test();

}
