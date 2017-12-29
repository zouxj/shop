package com.shenyu.laikaword.module.us;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity.AccountBindActivity;
import com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity.BoundPhoneActivity;
import com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity.ChangeBindPhoneActivity;
import com.shenyu.laikaword.module.us.setpassword.SetPassWordMsgCodeActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountSecurityActivity extends LKWordBaseActivity {


    @BindView(R.id.tv_upate_phone)
    TextView tvUpatePhone;
    @BindView(R.id.head_user_back)
    ImageView headUserBack;
    @BindView(R.id.rl_bd_phone)
    RelativeLayout rlBdPhone;
    @BindView(R.id.head_user_name)
    ImageView headUserName;
    @BindView(R.id.rl_bd_account)
    RelativeLayout rlBdAccount;
    @BindView(R.id.head_user_qq)
    ImageView headUserQq;
    @BindView(R.id.rl_seting_pay_password)
    RelativeLayout rlSetingPayPassword;

    @Override
    public int bindLayout() {
        return R.layout.activity_account_security;
    }

    @Override
    public void initView() {
        setToolBarTitle("账号与安全");
    }

    @Override
    public void doBusiness(Context context) {
        tvUpatePhone.setText(Constants.getLoginReponse().getPayload().getBindPhone());
    }

    @Override
    public void setupActivityComponent() {

    }


    @OnClick({R.id.rl_bd_phone, R.id.rl_bd_account, R.id.rl_seting_pay_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bd_phone:
                //TODO 修改手机号
                vertorlpassWord();
                break;
            case R.id.rl_bd_account:
                //TODO 绑定账号
                IntentLauncher.with(mActivity).launch(AccountBindActivity.class);
                break;
            case R.id.rl_seting_pay_password:
                //TODO设置支付密码
                IntentLauncher.with(mActivity).launch(SetPassWordMsgCodeActivity.class);
                break;
        }
    }
    protected void vertorlpassWord() {
        LoginReponse loginReponse= Constants.getLoginReponse();
        if (loginReponse.getPayload().getIsSetTransactionPIN()==0){
            DialogHelper.commonDialog(mActivity, "温馨提示", "您尚未设置支付密码", "取消", "去设置", false, new DialogHelper.ButtonCallback() {
                @Override
                public void onNegative(Dialog dialog) {
                    IntentLauncher.with(mActivity).launch(SetPassWordMsgCodeActivity.class);
                }

                @Override
                public void onPositive(Dialog dialog) {

                }
            }).show();
            return;
        }
        DialogHelper.setInputDialog(mActivity, true,null,"", new DialogHelper.LinstenrText() {
            @Override
            public void onLintenerText(String passWord) {
                retrofitUtils.setLifecycleTransformer(bindToLifecycle()).addSubscription(RetrofitUtils.apiStores.validateTransactionPIN(passWord), new ApiCallback<BaseReponse>() {
                    @Override
                    public void onSuccess(BaseReponse model) {
                        if (model.isSuccess())
                            IntentLauncher.with(mActivity).launch(ChangeBindPhoneActivity.class);
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

            @Override
            public void onWjPassword() {

            }
        }).show();

    }
}
