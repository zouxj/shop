package com.zxj.parlibary.resultlistener;

/**
 * Created by Administrator on 2017/8/10 0010.
 */


/**
 * 微信支付监听
 * @author Administrator
 *
 */
public interface OnWechatPayListener{
     void onPaySuccess(int errorCode);
     void onPayFailure(int errorCode);
}