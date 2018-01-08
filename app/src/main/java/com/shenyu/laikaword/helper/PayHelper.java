package com.shenyu.laikaword.helper;

import android.app.Activity;

import com.shenyu.laikaword.model.bean.reponse.WeixinPayReponse;
import com.zxj.parlibary.PayAPI;
import com.zxj.parlibary.alipay.AliPayAPI;
import com.zxj.parlibary.alipay.AliPayReq2;
import com.zxj.parlibary.qqpay.QQPayAPI;
import com.zxj.parlibary.qqpay.QQPayReq;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.parlibary.wechatpay.WechatPayReq;

/**
 * Created by shenyu_zxjCode on 2017/9/26 0026.
 * 支付帮助类
 */

public final class PayHelper {
    /**
     * 微信支付Test
     * 微信支付
     */
    public static  void wechatPay(Activity activity, WeixinPayReponse weixinPayReponse){
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(activity) //activity实例
                .setAppId(weixinPayReponse.getPayload().getPayInfo().getAppid()) //微信支付AppID
                .setPartnerId(weixinPayReponse.getPayload().getPayInfo().getPartnerid())//微信支付商户号
                .setPrepayId(weixinPayReponse.getPayload().getPayInfo().getPrepayid())//预支付码
				.setPackageValue(weixinPayReponse.getPayload().getPayInfo().getPackageX())//"Sign=WXPay"
                .setNonceStr(weixinPayReponse.getPayload().getPayInfo().getNoncestr())
                .setTimeStamp(String.valueOf(weixinPayReponse.getPayload().getPayInfo().getTimestamp()))//时间戳
                .setSign(weixinPayReponse.getPayload().getPayInfo().getSign())//签名
                .create();
        PayAPI.getInstance().sendPayRequest(wechatPayReq);
//        WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
    }

    /**
     * 安全的支付宝支付测试
     * 支付宝支付
     */


    public static  void aliPaySafely(String orderInfo, Activity activity, OnAliPayListener onAliPayListener){
        AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                .with(activity)//Activity实例
                .setSignedAliPayOrderInfo(orderInfo) //set the signed ali pay order info
                .create()//
                .setOnAliPayListener(onAliPayListener);//
        AliPayAPI.getInstance().sendPayReq(aliPayReq);
    }

    /**
     * QQ支付
     * @param activity
     * @param onAliPayListener
     */
    public static void qqPay(Activity activity, QqPayListener onAliPayListener){
        String appId="";
        String  nonce=String.valueOf(System.currentTimeMillis());;
        long  timeStamp =System.currentTimeMillis() / 1000;
        String  tokenId="";
        String  pubAcc="";
        String  pubAccHint="";
        String  bargainorId="";
        String  sigType="";
        String  sig="";
        QQPayReq qqPayReq = new QQPayReq.Builder()
                .with(activity) //activity实例
                .setAppId(appId)
                .setTokenId(tokenId)
                .setBargainorId(bargainorId)
                .setNonce(nonce)
                .setPubAcc(pubAcc)
                .setPubAccHint(pubAccHint)
                .setTimeStamp(timeStamp)
                .setSigType(sigType)
                .setSig(sig)
                .create();
        QQPayAPI.getInstance().sendPayReq(qqPayReq, onAliPayListener);
    }
}
