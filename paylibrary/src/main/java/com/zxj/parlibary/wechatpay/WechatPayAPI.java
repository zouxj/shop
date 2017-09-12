package com.zxj.parlibary.wechatpay;

/**
 * Created by Zxjcode on 2017/8/10 0010.
 * 微信支付
 */

public class WechatPayAPI {
    /**
     * 获取微信支付API
     */
    private static final Object mLock = new Object();
    private static WechatPayAPI mInstance;

    public static WechatPayAPI getInstance(){
        if(mInstance == null){
            synchronized (mLock){
                if(mInstance == null){
                    mInstance = new WechatPayAPI();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送微信支付请求
     * @param wechatPayReq
     */
    public void sendPayReq(WechatPayReq wechatPayReq){
        wechatPayReq.send();
    }
}
