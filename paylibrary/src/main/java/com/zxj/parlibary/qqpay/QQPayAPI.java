package com.zxj.parlibary.qqpay;

import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.parlibary.wechatpay.WechatPayAPI;
import com.zxj.parlibary.wechatpay.WechatPayReq;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class QQPayAPI {
    /**
     * 获取QQ支付API
     */
    private static final Object mLock = new Object();
    private static QQPayAPI mInstance;

    public static QQPayAPI getInstance(){
        if(mInstance == null){
            synchronized (mLock){
                if(mInstance == null){
                    mInstance = new QQPayAPI();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送QQ支付请求
     * @param qqPayReq
     */
    public void sendPayReq(QQPayReq qqPayReq, QqPayListener qqPayListener){
        qqPayReq.setQQPayListener(qqPayListener);
        qqPayReq.send();
    }
}
