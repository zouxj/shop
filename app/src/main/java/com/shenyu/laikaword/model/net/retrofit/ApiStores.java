package com.shenyu.laikaword.model.net.retrofit;

import com.shenyu.laikaword.model.bean.reponse.BankReponse;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.AddressReponse;
import com.shenyu.laikaword.model.bean.reponse.BankInfoReponse;
import com.shenyu.laikaword.model.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.model.bean.reponse.CheckAppUpdateReponse;
import com.shenyu.laikaword.model.bean.reponse.GoodsListReponse;
import com.shenyu.laikaword.model.bean.reponse.ImgSTSReponse;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.MessageReponse;
import com.shenyu.laikaword.model.bean.reponse.MoneyDetailReponse;
import com.shenyu.laikaword.model.bean.reponse.MsgCodeReponse;
import com.shenyu.laikaword.model.bean.reponse.OrderListReponse;
import com.shenyu.laikaword.model.bean.reponse.PayInfoReponse;
import com.shenyu.laikaword.model.bean.reponse.PickUpGoodsReponse;
import com.shenyu.laikaword.model.bean.reponse.ResellParticularsReponse;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.model.bean.reponse.StartBannerGuangKReponse;
import com.shenyu.laikaword.model.bean.reponse.WeixinPayReponse;
import com.shenyu.laikaword.model.bean.reponse.ZhuanMaiReponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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
    Observable<BaseReponse> getSMCode(@Query("phone")String phone,@Query("codeType")String codeType);
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
    Observable<CheckAppUpdateReponse> checkUpdate(@Query("t")int i,@Query("version")String version);
    @Streaming
    @GET()//下载apk
    Call<ResponseBody> downApk(@Url String url);
    @FormUrlEncoded
    @POST("common/validateSMSCode")//校验支付密码
    Observable<MsgCodeReponse> validateSMSCode(@Field("codeType") String codeType, @Field("code") String code);
    @FormUrlEncoded
    @POST("user/validateTransactionPIN")//校验支付密码
    Observable<BaseReponse> validateTransactionPIN(@Field("transactionPIN") String transactionPIN);
    @GET("goods/list")//请求商品列表
    Observable<GoodsListReponse> getGoodsList(@Query("type") String type, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("common/getSTS")//获取图片上传STS
    Observable<ImgSTSReponse> getSTS();
    @GET("order/cardPack")//获取卡包
    Observable<BaseReponse> cardPack(@Query("type")int type);
    @FormUrlEncoded
    @POST("account/createRechargeOrder")//余额充值
    Observable<MsgCodeReponse> createRechargeOrder(@Field("money") String money, @Field("payWay") String payWay);
    @FormUrlEncoded
    @POST("user/withDrawMoney")//余额提现
    Observable<BaseReponse> withdrawMoney(@Field("money") String money,@Field("cardId") String cardId,@Field("transactionPIN") String transactionPIN);
    @GET("user/messageList")//获取消息
    Observable<MessageReponse> messageList();
    @GET("order/myOrderList")//购买记录
    Observable<OrderListReponse> myOrderList(@Query("page") int page,@Query("pageSize") int pageSize);
    @FormUrlEncoded
    @POST("account/rechargemoney")//余额充值
    Observable<PayInfoReponse> rechargeMoney(@Field("money") String money, @Field("payWay") int payWay);

    @FormUrlEncoded
    @POST("account/rechargemoney")//余额充值
    Observable<WeixinPayReponse> rechargeWxMoney(@Field("money") String money, @Field("payWay") int payWay);
    @GET("user/cardPackage")//我的卡包
    Observable<CarPagerReponse> cardPackage();
    @FormUrlEncoded
    @POST("order/create")//下单接口
    Observable<PayInfoReponse> createOrder(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("order/create")//微信下单接口
    Observable<WeixinPayReponse> wxcreateOrder(@FieldMap Map<String, String> map);

    @POST("account/extractPackage")//下单接口
    @FormUrlEncoded
    Observable<BaseReponse> extractPackage(@FieldMap Map<String, String> map);
    @FormUrlEncoded
    @POST("account/newMyExtract")// 我的提货接口
    Observable<PickUpGoodsReponse> newMyExtract(@Field("extractType") int extractType,@Field("page") int page,@Field("pageSize") int pageSize);
    @FormUrlEncoded
    @POST("account/getUserMoneyDetail")//余额明细
    Observable<MoneyDetailReponse> getUserMoneyDetail(@Field("page") int page, @Field("pageSize") int pageSize);
    @FormUrlEncoded
    @POST("user/partyBind")//余额明细
    Observable<LoginReponse> partyBind(@FieldMap Map<String, String> map);
    @GET("common/appStartUp")//启动页
    Observable<StartBannerGuangKReponse> appStartUp();
    @GET("resell/list")// 我的转卖接口
    Observable<ZhuanMaiReponse> resellList(@Query("status") int status, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("resell/detail")// 我的转卖接口
    Observable<ResellParticularsReponse> resellDetail(@Query("goodsId") String goodsId);
    @FormUrlEncoded
    @POST("user/getCardAccountInfo")//银行卡鉴权
    Observable<BankReponse> getCardAccountInfo(@Field("cardNo") String cardNo);

    @FormUrlEncoded
    @POST("user/changePhone")//更换手机号码
    Observable<BaseReponse> changeBindPhonePresenter(@Field("phone") String phone);
    //登录接口
    @FormUrlEncoded
    @POST("user/changePhone")
    Observable<BaseReponse> changePhone(@Field("phone") String phone, @Field("code") String code);


}
