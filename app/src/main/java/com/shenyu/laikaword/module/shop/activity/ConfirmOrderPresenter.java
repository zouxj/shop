package com.shenyu.laikaword.module.shop.activity;

import android.app.Activity;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.helper.PayHelper;
import com.shenyu.laikaword.module.shop.ConfirmOrderView;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.parlibary.resultlistener.OnWechatPayListener;
import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.shenyu.laikaword.helper.ViewUtils;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */

public class ConfirmOrderPresenter extends BasePresenter<ConfirmOrderView> {

    private Activity mActivity;
    public ConfirmOrderPresenter(Activity activity,ConfirmOrderView confirmOrderView){
        this.mvpView = confirmOrderView;
        this.mActivity=activity;
    }

    //订单支付
    public void cofirmPay(int Type){
        //TODO 根据支付类型去实现支付方式
        switch (Type){
            case 0:
                //TODO 余额支付看有没有设置面，没有就去设置,相反就输密码支付
                ViewUtils.setInputDialog(mActivity, true, new ViewUtils.LinstenrText() {
                    @Override
                    public void onLintenerText(String passWord) {
                        ToastUtil.showToastShort("请求密码:===>"+passWord);
                        IntentLauncher.with(mActivity).launch(PaySuccessActivity.class);
                    }
                }).show();
                break;
            case 1:
                mvpView.paySuccess();
                //TODO 支付宝支付
                PayHelper.testAliPaySafely(mActivity, new OnAliPayListener() {
                    @Override
                    public void onPaySuccess(String resultInfo) {

                    }

                    @Override
                    public void onPayFailure(String resultInfo) {

                    }

                    @Override
                    public void onPayConfirmimg(String resultInfo) {

                    }

                    @Override
                    public void onPayCheck(String status) {

                    }
                });
                break;
            case 2:
                PayHelper.testQQPay(mActivity, new QqPayListener() {
                    @Override
                    public void onPaySuccess(int successCode) {

                    }

                    @Override
                    public void onPayFailure(int errorCode) {

                    }
                });
                //TODO QQ钱包支付
                break;
            case 3:
                PayHelper.testWechatPay(mActivity, new OnWechatPayListener() {
                    @Override
                    public void onPaySuccess(int errorCode) {

                    }

                    @Override
                    public void onPayFailure(int errorCode) {

                    }
                });
                //TODO 微信支付
                break;
        }
    }

}
