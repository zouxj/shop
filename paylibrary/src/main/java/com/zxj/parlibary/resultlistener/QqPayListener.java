package com.zxj.parlibary.resultlistener;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

/**
 * 微信支付监听
 * @author Administrator
 *
 */
public interface QqPayListener {
    public  void onPaySuccess(int successCode);
    public  void onPayFailure(int errorCode);
}