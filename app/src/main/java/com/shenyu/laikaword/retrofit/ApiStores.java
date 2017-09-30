package com.shenyu.laikaword.retrofit;

import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.ReRequest;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public interface ApiStores {
    //登录接口
    @FormUrlEncoded
    @POST("user/phoneLogin")
    Observable<LoginReponse> loginPhone(@Field("phone") String phone, @Field("code") String code);
    //QQ或者微信第三方登录
    @FormUrlEncoded
    @POST("user/partyLogin")
    Observable<LoginReponse> loginWxQQ(@Field("loginType") String loginType, @Field("code") String code,@Field("openId") String openId, @Field("accessToken") String accessToken);
    //获取主页面数据
    @GET("common/appMain")
    Observable<ShopMainReponse> getMainShop();
    //获取短信验证码
    @GET("common/getSMSCode")
    Observable<ShopMainReponse> getSMCode(@Query("phone")String phone,@Query("codeType")String codeType);
    @FormUrlEncoded
    @POST("user/setAddress")
    Observable<BaseReponse> setAddress(@FieldMap Map<String, String> map);//设置请求地址
    @FormUrlEncoded
    @POST("user/setAddress")
    Observable<BaseReponse> deleteAddress(@Field("addressId")String addressId);//删除地址


}
