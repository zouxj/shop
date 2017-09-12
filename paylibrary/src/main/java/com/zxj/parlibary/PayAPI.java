package com.zxj.parlibary;

import com.zxj.parlibary.alipay.AliPayAPI;
import com.zxj.parlibary.alipay.AliPayReq;
import com.zxj.parlibary.alipay.AliPayReq2;
import com.zxj.parlibary.wechatpay.WechatPayAPI;
import com.zxj.parlibary.wechatpay.WechatPayReq;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class PayAPI {
    private static final Object mLock = new Object();
    private static PayAPI mInstance;

    public static PayAPI getInstance(){
        if(mInstance == null){
            synchronized (mLock){
                if(mInstance == null){
                    mInstance = new PayAPI();
                }
            }
        }
        return mInstance;
    }


    /**
     * 支付宝支付请求
     * @param aliPayRe
     */
    public void sendPayRequest(AliPayReq aliPayRe){
        AliPayAPI.getInstance().sendPayReq(aliPayRe);
    }

    /**
     * 支付宝支付请求 - 避免商户私钥暴露在客户端
     * @param aliPayRe2
     */
    public void sendPayRequest(AliPayReq2 aliPayRe2){
        AliPayAPI.getInstance().sendPayReq(aliPayRe2);
    }



    /**
     * 微信支付请求
     * @param wechatPayReq
     */
    public void sendPayRequest(WechatPayReq wechatPayReq){
        WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
    }
}
