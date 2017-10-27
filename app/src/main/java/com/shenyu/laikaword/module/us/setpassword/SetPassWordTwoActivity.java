package com.shenyu.laikaword.module.us.setpassword;

import android.content.Context;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.base.BaseReponse;
import com.shenyu.laikaword.module.goods.order.ui.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.widget.countdownview.PayPsdInputView;

import butterknife.BindView;

public class SetPassWordTwoActivity extends LKWordBaseActivity {
    @BindView(R.id.psd_view_password)
    PayPsdInputView psdViewPassword;
    @Override
    public int bindLayout() {
        return R.layout.activity_set_pass_word_two;
    }

    @Override
    public void doBusiness(Context context) {
      String typeActivity = getIntent().getStringExtra("typeActivity");
        if (typeActivity!=null&&typeActivity.equals("RESERT"))
            setToolBarTitle("重置支付密码");
        else
            setToolBarTitle("再次设置密码");
        String passwordOne = getIntent().getStringExtra("PAYPASSWORD");
        final String codeToken = getIntent().getStringExtra("codeToken");
        KeyBoardUtil.showSoftInput(psdViewPassword);
        psdViewPassword.setComparePassword(passwordOne, new PayPsdInputView.onPasswordListener() {
            @Override
            public void onDifference() {
                ToastUtil.showToastShort("二次密码不一样");
            }

            @Override
            public void onEqual(String psd) {
                RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.setTransactionPIN(psd, codeToken), new ApiCallback<BaseReponse>() {
                    @Override
                    public void onSuccess(BaseReponse model) {
                        if (model.isSuccess()) {
                            RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                            IntentLauncher.with(SetPassWordTwoActivity.this).launch(ConfirmOrderActivity.class);
                            finish();
                        }
                        else {
                            ToastUtil.showToastShort(model.getError().getMessage());
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
        });

    }

    @Override
    public void setupActivityComponent() {

    }
}
