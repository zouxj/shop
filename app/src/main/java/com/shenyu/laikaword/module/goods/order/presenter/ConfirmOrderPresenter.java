package com.shenyu.laikaword.module.goods.order.presenter;

import android.app.Activity;
import android.app.Dialog;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.GoodBean;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.PayInfoReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.PayHelper;
import com.shenyu.laikaword.model.bean.reponse.WeixinPayReponse;
import com.shenyu.laikaword.model.rxjava.rx.RxTask;
import com.shenyu.laikaword.module.goods.order.ShopSuccessActivity;
import com.shenyu.laikaword.module.us.setpassword.SetPassWordMsgCodeActivity;
import com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity.BoundPhoneActivity;
import com.shenyu.laikaword.module.goods.order.view.ConfirmOrderView;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.us.wallet.recharge.RechargeMoneyActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.shenyu.laikaword.helper.DialogHelper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import rx.Observable;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */

public class ConfirmOrderPresenter extends BasePresenter<ConfirmOrderView> {

    private Activity mActivity;
    private LoginReponse loginReponse;
    public ConfirmOrderPresenter(Activity activity,ConfirmOrderView confirmOrderView){
        this.mvpView = confirmOrderView;
        this.mActivity=activity;
        attachView(mvpView);
    }

    //????????????
    public void cofirmPay(final LifecycleTransformer lifecycleTransformer, final int type, final int count, final String zecount, String goodsID){
        //TODO ???????????????????????????????????????
         loginReponse = Constants.getLoginReponse();
        if (null!=loginReponse){
            if (!StringUtil.validText(loginReponse.getPayload().getBindPhone())){
                //????????????????????????????????????
                DialogHelper.commonDialog(mActivity, "????????????", "???????????????????????????!????????????????", "??????", "?????????", false, new DialogHelper.ButtonCallback() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        IntentLauncher.with(mActivity).launch(BoundPhoneActivity.class);
                    }

                    @Override
                    public void onPositive(Dialog dialog) {

                    }
                }).show();
            }else {
                switch (type){
                    case 5:
                        //????????????????????????
                        new RxTask().addSubscription(lifecycleTransformer, apiStores.getStock(goodsID, count + "").map(new Function<BaseReponse, Boolean>() {
                            @Override
                            public Boolean apply(BaseReponse baseReponse){
                                if (!baseReponse.isSuccess())
                                    ToastUtil.showToastShort(baseReponse.getError().getMessage());

                                return baseReponse.isSuccess();
                            }
                        }), new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Boolean o) {
                                if (o)
                                yuePay(lifecycleTransformer,type, count, zecount);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                        //                        //TODO ????????????
                        break;
                    case 3:
                        aliPay(lifecycleTransformer,type, count, zecount);
                        //TODO ???????????????
                        break;
                    case 2:

                        //TODO qq????????????
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
                        //TODO QQ????????????
                        break;
                    case 1:
                        //TODO ????????????
                        wxPay(lifecycleTransformer,type, count, zecount);

                        break;
                }
            }
        }





    }


    private void yuePay(final LifecycleTransformer lifecycleTransformermr, final int type, final int count, final String zecount) {
        Double money = StringUtil.formatDouble(loginReponse.getPayload().getMoney());
        if (money<StringUtil.formatDouble(zecount)) {
            //TODO ?????????????????????????????????????????????
            DialogHelper.commonDialog(mActivity, "????????????", "????????????????????????????????????", "??????", "?????????", false, new DialogHelper.ButtonCallback() {
                @Override
                public void onNegative(Dialog dialog) {
                    IntentLauncher.with(mActivity).launch(RechargeMoneyActivity.class);
                }

                @Override
                public void onPositive(Dialog dialog) {

                }
            }).show();
        }else if (loginReponse.getPayload().getIsSetTransactionPIN()==0){
            DialogHelper.commonDialog(mActivity, "????????????", "???????????????????????????", "??????", "?????????", false, new DialogHelper.ButtonCallback() {
                @Override
                public void onNegative(Dialog dialog) {
                    IntentLauncher.with(mActivity).launch(SetPassWordMsgCodeActivity.class);
                }

                @Override
                public void onPositive(Dialog dialog) {

                }
            }).show();
        }else if(loginReponse.getPayload().getIsSetTransactionPIN()!=0){
            //TODO ??????????????????????????????,???????????????
            DialogHelper.setInputDialog(mActivity, true,zecount, "",new DialogHelper.LinstenrText() {
                @Override
                public void onLintenerText(final String passWord) {

                    Map<String,String> param = new HashMap<>();
                    param.put("goodsId",goodBean.getGoodsId());
                    param.put("amount",zecount);
                    param.put("quantity",count+"");
                    param.put("payWay",type+"");
                    param.put("transactionPIN",passWord);
                    yuePay(lifecycleTransformermr,param);

                }

                @Override
                public void onWjPassword() {
                }
            }).show();
        }
    }

    /**
     * ????????????
     * @param lifecycleTransformer
     * @param type
     * @param count
     * @param zecount
     */
    private void wxPay(LifecycleTransformer lifecycleTransformer,int type, int count, String zecount) {
        Map<String,String> param = new HashMap<>();
        param.put("goodsId",goodBean.getGoodsId());
        param.put("amount",zecount);
        param.put("quantity",count+"");
        param.put("payWay",type+"");
        addSubscription(lifecycleTransformer,apiStores.wxcreateOrder(param), new ApiCallback<WeixinPayReponse>() {
            @Override
            public void onSuccess(WeixinPayReponse model) {
                if (model.isSuccess()) {
                    Constants.PAY_WX_TYPE=2;
                     PayHelper.wechatPay(mActivity,model);
                }

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
    /**
     * ???????????????
     * @param lifecycleTransformer
     * @param type
     * @param count
     * @param zecount
     */
    private void aliPay(LifecycleTransformer lifecycleTransformer,int type, int count, String zecount) {
        //TODO ???????????????
        Map<String,String> param = new HashMap<>();
        param.put("goodsId",goodBean.getGoodsId());
        param.put("amount",zecount);
        param.put("quantity",count+"");
        param.put("payWay",type+"");
        addSubscription(lifecycleTransformer,apiStores.createOrder(param), new ApiCallback<PayInfoReponse>() {
                @Override
                public void onSuccess(PayInfoReponse model) {
                    if (model.isSuccess()) {
                        PayHelper.aliPaySafely(model.getPayload().getPayInfo(), mActivity, new OnAliPayListener() {

                            @Override
                            public void onNext(String resultInfo) {
                                if (resultInfo.equals("9000")) {
                                    RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                                    IntentLauncher.with(mActivity).launch(ShopSuccessActivity.class);
                                } else if (resultInfo.equals("8000"))
                                    ToastUtil.showToastShort("?????????????????????");
                                else if (resultInfo.equals("6001"))
                                    ToastUtil.showToastShort("????????????");
                                else
                                    ToastUtil.showToastShort("????????????");
                            }
                        });
                    }

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

    GoodBean goodBean;
    /**
     * ???????????????
     */
    public void initData(){
        loginReponse  = Constants.getLoginReponse();
         goodBean = (GoodBean)mActivity. getIntent().getSerializableExtra("order");
        mvpView.showData(loginReponse,goodBean);
    }

    /**
     * ???????????????????????????
     */
    public void checkoutBindPone(){
        LoginReponse loginReponse = Constants.getLoginReponse();
        if (StringUtil.validText(loginReponse.getPayload().getBindPhone())){
            //??????????????????
            IntentLauncher.with(mActivity).launch(BoundPhoneActivity.class);
        }
    }

    public   void yuePay(LifecycleTransformer lifecycleTransformer,Map<String,String> param){
       addSubscription(lifecycleTransformer,apiStores.createOrder(param), new ApiCallback<PayInfoReponse>() {
            @Override
            public void onSuccess(PayInfoReponse model) {
                if (model.isSuccess()) {
                    RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                    IntentLauncher.with(mActivity).launchFinishCpresent(ShopSuccessActivity.class);
                }


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
