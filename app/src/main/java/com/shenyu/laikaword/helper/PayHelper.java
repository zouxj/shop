package com.shenyu.laikaword.helper;

import android.app.Activity;

import com.zxj.parlibary.alipay.AliPayAPI;
import com.zxj.parlibary.alipay.AliPayReq2;
import com.zxj.parlibary.qqpay.QQPayAPI;
import com.zxj.parlibary.qqpay.QQPayReq;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.parlibary.resultlistener.OnWechatPayListener;
import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.parlibary.wechatpay.WechatPayAPI;
import com.zxj.parlibary.wechatpay.WechatPayReq;
import com.zxj.utilslibrary.utils.ToastUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/26 0026.
 * 支付帮助类
 */

public class PayHelper {
    /**
     * 微信支付Test
     * 微信支付
     */
    public static  void testWechatPay(Activity activity,OnWechatPayListener onWechatPayListener){
        String appid        = "";
        String partnerid    = "";
        String prepayid     = "";
        String noncestr     = "";
        String timestamp    = "";
        String sign         = "";
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(activity) //activity实例
                .setAppId(appid) //微信支付AppID
                .setPartnerId(partnerid)//微信支付商户号
                .setPrepayId(prepayid)//预支付码
//				.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
                .setNonceStr(noncestr)
                .setTimeStamp(timestamp)//时间戳
                .setSign(sign)//签名
                .create();

        wechatPayReq.setOnWechatPayListener(onWechatPayListener);
        WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
    }

    /**
     * 安全的支付宝支付测试
     * 支付宝支付
     */
    public static  void testAliPaySafely(Activity activity,OnAliPayListener onAliPayListener){
        String partner          = "";
        String seller           = "";
        String outTradeNo       = "";
        String price            = "";
        String orderSubject     = "";
        String orderBody        = "";
        String callbackUrl      = "";


        String rawAliOrderInfo = new AliPayReq2.AliOrderInfo()
                .setPartner(partner) //商户PID || 签约合作者身份ID
                .setSeller(seller)  // 商户收款账号 || 签约卖家支付宝账号
                .setOutTradeNo(outTradeNo) //设置唯一订单号
                .setSubject(orderSubject) //设置订单标题
                .setBody(orderBody) //设置订单内容
                .setPrice(price) //设置订单价格
                .setCallbackUrl(callbackUrl) //设置回调链接
                .createOrderInfo(); //创建支付宝支付订单信息


        //TODO 这里需要从服务器获取用商户私钥签名之后的订单信息
//        String signAliOrderInfo = getSignAliOrderInfoFromServer(rawAliOrderInfo);
        String signAliOrderInfo = null;
        AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                .with(activity)//Activity实例
                .setRawAliPayOrderInfo(rawAliOrderInfo)//set the ali pay order info
                .setSignedAliPayOrderInfo(signAliOrderInfo) //set the signed ali pay order info
                .create()//
                .setOnAliPayListener(onAliPayListener);//
        AliPayAPI.getInstance().sendPayReq(aliPayReq);
    }

    /**
     * QQ支付
     * @param activity
     * @param onAliPayListener
     */
    public static void testQQPay(Activity activity,QqPayListener onAliPayListener){
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
