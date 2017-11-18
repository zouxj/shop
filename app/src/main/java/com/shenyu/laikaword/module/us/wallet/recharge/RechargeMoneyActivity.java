package com.shenyu.laikaword.module.us.wallet.recharge;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.PayInfoReponse;
import com.shenyu.laikaword.helper.PayHelper;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.us.appsetting.acountbind.BoundPhoneActivity;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 充值
 */
public class RechargeMoneyActivity extends LKWordBaseActivity {
    @BindView(R.id.recharge_rb_num)
    EditText rechargeRbNum;
    @BindView(R.id.recharge_rb_alipay)
    RadioButton rechargeRbAlipay;
    @BindView(R.id.recharge_rb_qqpay)
    RadioButton rechargeRbQqpay;
    @BindView(R.id.recharge_tv_next)
    TextView rechargeTvNext;
    @BindView(R.id.recharge_rg)
    RadioGroup radioGroup;
    private int type =3;
    @Override
    public int bindLayout() {
        return R.layout.activity_recharge_money;
    }

    @Override
    public void initView() {
        setToolBarTitle("余额充值");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int positon) {
                switch (positon){
                    case R.id.recharge_rb_alipay:
                        //TODO 阿里支付
                        type=3;
                        break;
                    case R.id.recharge_rb_qqpay:
                        //TODO qq支付
                        type=2;
                        break;
                }
            }
        });
    }
    @OnClick({R.id.recharge_tv_next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.recharge_tv_next:
                LoginReponse loginReponse = Constants.getLoginReponse();
                if (null!=loginReponse){
                    if (!StringUtil.validText(loginReponse.getPayload().getBindPhone())) {
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
                        return;
                    }

                }
                if (type==3){
                   retrofitUtils.addSubscription(RetrofitUtils.apiStores.rechargeMoney(rechargeRbNum.getText().toString().trim(), type), new ApiCallback<PayInfoReponse>() {
                        @Override
                        public void onSuccess(PayInfoReponse model) {
                            PayHelper.aliPaySafely(model.getPayload().getPayInfo(),RechargeMoneyActivity.this, new OnAliPayListener() {

                                @Override
                                public void onNext(String resultInfo) {
                                    if (resultInfo.equals("9000")) {
                                        RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                                        IntentLauncher.with(RechargeMoneyActivity.this).put("pay_type",type+"").put("money",rechargeRbNum.getText().toString().trim()).launchFinishCpresent(RechargeSuccessActivity.class);
                                    }
                                    else if(resultInfo.equals("8000"))
                                        ToastUtil.showToastShort("支付失败");
                                    else if(resultInfo.equals("6001"))
                                        ToastUtil.showToastShort("支付取消");
                                    else
                                        ToastUtil.showToastShort(resultInfo);
                                }
                            });
                        }

                        @Override
                        public void onFailure(String msg) {
                            ToastUtil.showToastShort(msg);
                        }

                        @Override
                        public void onFinish() {

                        }
                    });

                }else {
                    //TODO qq支付
                    PayHelper.qqPay(this, new QqPayListener() {
                        @Override
                        public void onPaySuccess(int successCode) {

                        }

                        @Override
                        public void onPayFailure(int errorCode) {

                        }
                    });
////
                }
//
                break;
        }
    }
    @Override
    public void doBusiness(Context context) {
        RxTextView.textChanges(rechargeRbNum).subscribe(new Action1<CharSequence>() {
            @SuppressLint("NewApi")
            @Override
            public void call(CharSequence charSequence) {
              if (null!=charSequence.toString().trim()&&charSequence.toString().trim().length()>0) {
                  rechargeTvNext.setEnabled(true);
                  if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                      rechargeTvNext.setBackground(UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle_light));
                  else
                      rechargeTvNext.setBackgroundResource(R.drawable.bg_bt_login_rectangle_light);
              }
              else {
                  rechargeTvNext.setEnabled(false);
                  if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                      rechargeTvNext.setBackground(UIUtil.getDrawable(R.drawable.bg_gray_icon));
                  else
                      rechargeTvNext.setBackgroundResource(R.drawable.bg_gray_icon);

              }
            }
        });
    }

    @Override
    public void setupActivityComponent() {

    }

}
