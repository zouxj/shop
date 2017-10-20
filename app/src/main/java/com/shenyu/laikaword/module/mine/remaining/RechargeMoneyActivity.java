package com.shenyu.laikaword.module.mine.remaining;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.reponse.PayInfoReponse;
import com.shenyu.laikaword.helper.PayHelper;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.parlibary.resultlistener.OnWechatPayListener;
import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
                if (type==3){
                    //阿里支付
//                    RetrofitUtils.getRetrofitUtils().apiStores.rechargeMoney(rechargeRbNum.getText().toString().trim(), type).flatMap(new Func1<PayInfoReponse, Observable<String>>() {
//                        @Override
//                        public Observable<String> call(PayInfoReponse payInfoReponse) {
//                            LogUtil.i(Thread.currentThread()+"payinforeponse");
//                            return PayHelper.createIpObservable(payInfoReponse.getPayload().getPayInfo(),RechargeMoneyActivity.this);
//                        }
//                    }).subscribeOn(Schedulers.io())
//                       .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Action1<String>() {
//                                @Override
//                                public void call(String s) {
//                                    LogUtil.i(Thread.currentThread()+"Action1");
//                                ToastUtil.showToastShort(s);
//                                }
//                    });
                    RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.rechargeMoney(rechargeRbNum.getText().toString().trim(), type), new ApiCallback<PayInfoReponse>() {
                        @Override
                        public void onSuccess(PayInfoReponse model) {
                            PayHelper.testAliPaySafely(model.getPayload().getPayInfo(),RechargeMoneyActivity.this, new OnAliPayListener() {
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
                        }

                        @Override
                        public void onFailure(String msg) {

                        }

                        @Override
                        public void onFinish() {

                        }
                    });

                }else {
                    //TODO qq支付
                    PayHelper.testQQPay(this, new QqPayListener() {
                        @Override
                        public void onPaySuccess(int successCode) {

                        }

                        @Override
                        public void onPayFailure(int errorCode) {

                        }
                    });

                }

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
                  rechargeTvNext.setBackground(UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle_light));
              }
              else {
                  rechargeTvNext.setEnabled(false);
                  rechargeTvNext.setBackground(UIUtil.getDrawable(R.drawable.bg_gray_icon));

              }
            }
        });
    }

    @Override
    public void setupActivityComponent() {

    }

}
