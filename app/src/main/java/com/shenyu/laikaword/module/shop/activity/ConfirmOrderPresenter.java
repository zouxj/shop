package com.shenyu.laikaword.module.shop.activity;

import android.app.Activity;
import android.app.Dialog;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.reponse.GoodBean;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.bean.reponse.PayInfoReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.PayHelper;
import com.shenyu.laikaword.module.mine.setpassword.SetPassWordMsgCodeActivity;
import com.shenyu.laikaword.module.mine.setpassword.SetPassWordOneActivity;
import com.shenyu.laikaword.module.mine.setpassword.SetPassWordTwoActivity;
import com.shenyu.laikaword.module.mine.systemsetting.activity.BoundPhoneActivity;
import com.shenyu.laikaword.module.shop.ConfirmOrderView;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.parlibary.resultlistener.OnWechatPayListener;
import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SignUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.shenyu.laikaword.helper.DialogHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */

public class ConfirmOrderPresenter extends BasePresenter<ConfirmOrderView> {

    private Activity mActivity;
    public ConfirmOrderPresenter(Activity activity,ConfirmOrderView confirmOrderView){
        this.mvpView = confirmOrderView;
        this.mActivity=activity;
        attachView(mvpView);
    }

    //订单支付
    public void cofirmPay(final int type, final int count, final String zecount){
        //TODO 根据支付类型去实现支付方式

        switch (type){
            case 5:
                LoginReponse loginReponse = Constants.getLoginReponse();
                if (null!=loginReponse){
                    if (!StringUtil.validText(loginReponse.getPayload().getBindPhone())){
                        //第一步查看有没有绑定手机
                        IntentLauncher.with(mActivity).launch(BoundPhoneActivity.class);
                    } else if (StringUtil.validText(loginReponse.getPayload().getBindPhone())&&loginReponse.getPayload().getIsSetTransactionPIN()==0){
                       DialogHelper.makeUpdate(mActivity, "温馨提示", "您尚未设置支付密码", "取消", "去设置", false, new DialogHelper.ButtonCallback() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        IntentLauncher.with(mActivity).launch(SetPassWordMsgCodeActivity.class);
                    }

                    @Override
                    public void onPositive(Dialog dialog) {

                    }
                }).show();
                   }else if(StringUtil.validText(loginReponse.getPayload().getBindPhone())&&loginReponse.getPayload().getIsSetTransactionPIN()!=0){
                       //TODO 设置了密码和绑定手机,去输入校验
                       DialogHelper.setInputDialog(mActivity, true,zecount, new DialogHelper.LinstenrText() {
                           @Override
                           public void onLintenerText(final String passWord) {

                                          Map<String,String> param = new HashMap<>();
                                           param.put("goodsId",goodBean.getGoodsId());
                                           param.put("amount",zecount);
                                           param.put("quantity",count+"");
                                           param.put("payWay",type+"");
                                           param.put("pawd",SignUtil.md5(passWord));
                                            yuePay(param);

                           }

                           @Override
                           public void onWjPassword() {
                               IntentLauncher.with(mActivity).put("RESERT","RESERT").launch(SetPassWordMsgCodeActivity.class);
                           }
                       }).show();
                   }
                }


                break;
            case 3:
//                mvpView.paySuccess();
                //TODO 支付宝支付
                Map<String,String> param = new HashMap<>();
                param.put("goodsId",goodBean.getGoodsId());
                param.put("amount",zecount);
                param.put("quantity",count+"");
                param.put("payWay",type+"");
            addSubscription(apiStores.createOrder(param), new ApiCallback<PayInfoReponse>() {
                    @Override
                    public void onSuccess(PayInfoReponse model) {
                        if (model.isSuccess())
                            PayHelper.testAliPaySafely(model.getPayload().getPayInfo(),mActivity, new OnAliPayListener() {
                                @Override
                                public void onPaySuccess(String resultInfo) {
                                    IntentLauncher.with(mActivity).launch(PaySuccessActivity.class);
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
                        else
                            ToastUtil.showToastShort(model.getError().getMessage());

                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.showToastShort(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
                break;
            case 2:
                //TODO qq钱包支付
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
            case 1:
               //TODO 微信支付
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
    GoodBean goodBean;
    /**
     * 初始化数据
     */
    public void initData(){
        LoginReponse loginReponse = Constants.getLoginReponse();
         goodBean = (GoodBean)mActivity. getIntent().getSerializableExtra("order");
        mvpView.showData(loginReponse,goodBean);
    }

    /**
     * 校验是否绑定手机号
     */
    public void checkoutBindPone(){
        LoginReponse loginReponse = Constants.getLoginReponse();
        if (StringUtil.validText(loginReponse.getPayload().getBindPhone())){
            //没有绑定手机
            IntentLauncher.with(mActivity).launch(BoundPhoneActivity.class);
        }
    }

    public   void yuePay(Map<String,String> param){
       addSubscription(apiStores.createOrder(param), new ApiCallback<PayInfoReponse>() {
            @Override
            public void onSuccess(PayInfoReponse model) {
                if (model.isSuccess())
                    IntentLauncher.with(mActivity).launch(PaySuccessActivity.class);
                else
                    ToastUtil.showToastShort(model.getError().getMessage());

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.showToastShort(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
