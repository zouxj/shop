package com.shenyu.laikaword.module.goods.order.presenter;

import android.app.Activity;
import android.app.Dialog;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.GoodBean;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.PayInfoReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.PayHelper;
import com.shenyu.laikaword.module.goods.order.PaySuccessActivity;
import com.shenyu.laikaword.module.mine.setpassword.SetPassWordMsgCodeActivity;
import com.shenyu.laikaword.module.mine.appsetting.acountbind.BoundPhoneActivity;
import com.shenyu.laikaword.module.goods.order.view.ConfirmOrderView;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.utilslibrary.utils.IntentLauncher;
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
                        DialogHelper.makeUpdate(mActivity, "温馨提示", "你尚未绑定手机号码!请前往绑定?", "取消", "去绑定", false, new DialogHelper.ButtonCallback() {
                            @Override
                            public void onNegative(Dialog dialog) {
                                IntentLauncher.with(mActivity).launch(BoundPhoneActivity.class);
                            }

                            @Override
                            public void onPositive(Dialog dialog) {

                            }
                        }).show();

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
                                           param.put("transactionPIN",passWord);
                                            yuePay(param);

                           }

                           @Override
                           public void onWjPassword() {
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
                            PayHelper.aliPaySafely(model.getPayload().getPayInfo(),mActivity, new OnAliPayListener() {

                                @Override
                                public void onNext(String resultInfo) {
                                    if (resultInfo.equals("9000")) {
                                        RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                                        IntentLauncher.with(mActivity).launch(PaySuccessActivity.class);
                                    }
                                    else if (resultInfo.equals("8000"))
                                        ToastUtil.showToastShort("支付结果确认中");
                                    else if(resultInfo.equals("6001"))
                                        ToastUtil.showToastShort("支付取消");
                                    else
                                        ToastUtil.showToastShort("支付失败");
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
//                PayHelper.qqPay(mActivity, new QqPayListener() {
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
//                PayHelper.wechatPay(mActivity, new OnWechatPayListener() {
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
                if (model.isSuccess()) {
                    RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                    IntentLauncher.with(mActivity).launch(PaySuccessActivity.class);
                }
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