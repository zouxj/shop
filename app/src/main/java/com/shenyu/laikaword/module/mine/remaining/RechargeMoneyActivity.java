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
import com.shenyu.laikaword.helper.PayHelper;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.parlibary.resultlistener.OnWechatPayListener;
import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private int type =0;
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
                        type=0;
                        break;
                    case R.id.recharge_rb_qqpay:
                        //TODO qq支付
                        type=1;
                        break;
                }
            }
        });
    }
    @OnClick({R.id.recharge_tv_next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.recharge_tv_next:
                if (type==0){
                    //微信支付
                    PayHelper.testAliPaySafely(this, new OnAliPayListener() {
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
