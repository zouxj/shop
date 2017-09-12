package com.zxj.parlibary.resultlistener;

/**
 * Created by Administrator on 2017/8/10 0010.
 */
/**
 * 支付宝支付监听
 * @author Administrator
 *
 */
public interface OnAliPayListener{
    public void onPaySuccess(String resultInfo);
    public void onPayFailure(String resultInfo);
    public void onPayConfirmimg(String resultInfo);
    public void onPayCheck(String status);
}