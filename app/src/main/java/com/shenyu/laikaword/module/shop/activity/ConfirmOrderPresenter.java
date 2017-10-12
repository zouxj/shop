package com.shenyu.laikaword.module.shop.activity;

import android.app.Activity;
import android.app.Dialog;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.PayHelper;
import com.shenyu.laikaword.module.mine.setpassword.SetPassWordMsgCodeActivity;
import com.shenyu.laikaword.module.mine.setpassword.SetPassWordOneActivity;
import com.shenyu.laikaword.module.mine.setpassword.SetPassWordTwoActivity;
import com.shenyu.laikaword.module.shop.ConfirmOrderView;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.parlibary.resultlistener.OnWechatPayListener;
import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.shenyu.laikaword.helper.DialogHelper;

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
                LoginReponse loginReponse = Constants.getLoginReponse();
                if (null!=loginReponse){
                   if (loginReponse.getPayload().getIsSetTransactionPIN()==0){
                DialogHelper.makeUpdate(mActivity, "温馨提示", "您尚未设置支付密码", "取消", "去设置", false, new DialogHelper.ButtonCallback() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        IntentLauncher.with(mActivity).launch(SetPassWordMsgCodeActivity.class);
                    }

                    @Override
                    public void onPositive(Dialog dialog) {

                    }
                }).show();
                   }else{
                       //TODO 余额支付看有没有设置面，没有就去设置,相反就输密码支付
                       DialogHelper.setInputDialog(mActivity, true, new DialogHelper.LinstenrText() {
                           @Override
                           public void onLintenerText(String passWord) {
                               addSubscription(apiStores.validateTransactionPIN(passWord), new ApiCallback<BaseReponse>() {
                                   @Override
                                   public void onSuccess(BaseReponse model) {
                                       if (model.isSuccess())
                                           IntentLauncher.with(mActivity).launch(PaySuccessActivity.class);
                                       else
                                           ToastUtil.showToastLong(model.getError().getMessage());
                                   }

                                   @Override
                                   public void onFailure(String msg) {
                                       ToastUtil.showToastLong(msg);
                                   }

                                   @Override
                                   public void onFinish() {

                                   }
                               });

                           }

                           @Override
                           public void onWjPassword() {
                               IntentLauncher.with(mActivity).put("RESERT","RESERT").launch(SetPassWordMsgCodeActivity.class);
                           }
                       }).show();
                   }
                }


                break;
            case 1:
                mvpView.paySuccess();
                //TODO 支付宝支付
//                PayHelper.testAliPaySafely(mActivity, new OnAliPayListener() {
//                    @Override
//                    public void onPaySuccess(String resultInfo) {
//
//                    }
//
//                    @Override
//                    public void onPayFailure(String resultInfo) {
//
//                    }
//
//                    @Override
//                    public void onPayConfirmimg(String resultInfo) {
//
//                    }
//
//                    @Override
//                    public void onPayCheck(String status) {
//
//                    }
//                });
                break;
            case 2:
                mvpView.paySuccess();
//                PayHelper.testQQPay(mActivity, new QqPayListener() {
//                    @Override
//                    public void onPaySuccess(int successCode) {
//
//                    }
//
//                    @Override
//                    public void onPayFailure(int errorCode) {
//
//                    }
//                });
                //TODO QQ钱包支付
                break;
            case 3:
                mvpView.paySuccess();
//                PayHelper.testWechatPay(mActivity, new OnWechatPayListener() {
//                    @Override
//                    public void onPaySuccess(int errorCode) {
//
//                    }
//
//                    @Override
//                    public void onPayFailure(int errorCode) {
//
//                    }
//                });
                //TODO 微信支付
                break;
        }
    }

}
