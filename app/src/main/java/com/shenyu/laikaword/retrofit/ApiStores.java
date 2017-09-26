package com.shenyu.laikaword.retrofit;

import com.shenyu.laikaword.bean.ReRequest;
import com.shenyu.laikaword.bean.reponse.LoginReponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public interface ApiStores {
    //登录接口
    @FormUrlEncoded
    @POST("MApi/user/phoneLogin")
    Observable<LoginReponse> loginPhone(@Field("phone") String phone, @Field("code") String code);
    //QQ或者微信第三方登录
    @FormUrlEncoded
    @POST("MApi/user/partyLogin")
    Observable<LoginReponse> loginWxQQ(@Field("loginType") String loginType, @Field("code") String code,@Field("openId") String openId, @Field("accessToken") String accessToken);

}
