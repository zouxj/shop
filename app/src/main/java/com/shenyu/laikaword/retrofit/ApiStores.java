package com.shenyu.laikaword.retrofit;

import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.ReRequest;
import com.shenyu.laikaword.bean.reponse.AddressReponse;
import com.shenyu.laikaword.bean.reponse.BankInfoReponse;
import com.shenyu.laikaword.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.bean.reponse.CheckAppUpdateReponse;
import com.shenyu.laikaword.bean.reponse.GoodsBean;
import com.shenyu.laikaword.bean.reponse.ImgSTSReponse;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.bean.reponse.MessageReponse;
import com.shenyu.laikaword.bean.reponse.MsgCodeReponse;
import com.shenyu.laikaword.bean.reponse.OrderListReponse;
import com.shenyu.laikaword.bean.reponse.PayInfoReponse;
import com.shenyu.laikaword.bean.reponse.PickUpGoodsReponse;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
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
    @POST("user/deleteAddress")
    Observable<BaseReponse> deleteAddress(@Field("addressId")String addressId);//删除地址
    @GET ("user/getAddress")//获取收货地址
    Observable<AddressReponse> getAddress();//删除地址
    @GET("user/getBankCard")//获取银行卡地址
    Observable<BankInfoReponse>  getBankCard();
    @FormUrlEncoded
    @POST("user/setBankCard")
    Observable<BaseReponse> setBankCard(@FieldMap Map<String, String> map);
    @FormUrlEncoded
    @POST("user/deleteBankCard")
    Observable<BaseReponse> deleteBankCard(@Field("cardId")String cardId);
    @FormUrlEncoded
    @POST("user/editInfo")
    Observable<BaseReponse> editInfo(@Field("nickname") String nickName,@Field("avatar") String avatar);
    @FormUrlEncoded
    @POST("user/bindPhone")//绑定手机号码
    Observable<BaseReponse> bindPhone(@Field("phone") String phone,@Field("code") String code);
    @FormUrlEncoded
    @POST("user/setTransactionPIN")//设置支付密码
    Observable<BaseReponse> setTransactionPIN(@Field("transactionPIN") String transactionPIN,@Field("SMSToken") String smsToken);
    //获取主页面数据
    @GET("user/info")
    Observable<LoginReponse> getUserInfo();
    @GET("common/checkUpdate")
    Observable<CheckAppUpdateReponse> checkUpdate(@Query("t")int i);
    @GET("temp/apk.apk")//下载apk
    Call<ResponseBody> downApk();
    @FormUrlEncoded
    @POST("common/validateSMSCode")//设置支付密码
    Observable<MsgCodeReponse> validateSMSCode(@Field("codeType") String codeType, @Field("code") String code);
    @FormUrlEncoded
    @POST("user/validateTransactionPIN")//校验支付密码
    Observable<BaseReponse> validateTransactionPIN(@Field("transactionPIN") String transactionPIN);
    @GET("goods/list")//请求商品列表
    Observable<ShopMainReponse> getGoodsList();
    @GET("common/getSTS")//获取图片上传STS
    Observable<ImgSTSReponse> getSTS();
    @GET("order/cardPack")//获取卡包
    Observable<BaseReponse> cardPack(@Query("type")int type);
    @FormUrlEncoded
    @POST("account/createRechargeOrder")//余额充值
    Observable<MsgCodeReponse> createRechargeOrder(@Field("money") String money, @Field("payWay") String payWay);
    @FormUrlEncoded
    @POST("user/withDrawMoney")//余额提现
    Observable<BaseReponse> withdrawMoney(@Field("money") String money,@Field("cardId") String cardId);
    @GET("user/messageList")//获取消息
    Observable<MessageReponse> messageList();
    @GET("transaction/orderList")//购买记录
    Observable<OrderListReponse> orderList();
    @FormUrlEncoded
    @POST("account/rechargemoney")//余额充值
    Observable<PayInfoReponse> rechargeMoney(@Field("money") String money, @Field("payWay") int payWay);
    @GET("user/cardPackage")//我的卡包
    Observable<CarPagerReponse> cardPackage();
    @FormUrlEncoded
    @POST("order/create")//下单接口
    Observable<PayInfoReponse> createOrder(@FieldMap Map<String, String> map);
    @POST("account/extractPackage")//下单接口
    @FormUrlEncoded
    Observable<BaseReponse> extractPackage(@FieldMap Map<String, String> map);
    @POST("account/myextract")//下单接口
    @FormUrlEncoded
    Observable<PickUpGoodsReponse> myextract(@Field("extractStatus") int extractStatus);



}
