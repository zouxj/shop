package com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.module.mine.BindAccountModule;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.appsetting.acountbind.presenter.ChangeBindPhonePresenter;
import com.shenyu.laikaword.module.us.appsetting.acountbind.view.ChangeBindPhoneView;
import com.zxj.utilslibrary.utils.IntentLauncher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更改绑定手机号码
 */
public class ChangeBindPhoneActivity extends LKWordBaseActivity implements ChangeBindPhoneView {


    @BindView(R.id.tv_phome)
    TextView tvPhome;
    @BindView(R.id.tv_msg_code)
    EditText tvMsgCode;
    @BindView(R.id.tv_current_phone)
    TextView tvCurrentPhone;
    @Inject
    ChangeBindPhonePresenter changeBindPhonePresenter;


    @Override
    public int bindLayout() {
        return R.layout.activity_change_bind_phone;
    }

    @Override
    public void initView() {
        setToolBarTitle("更换手机号");
    }

    @Override
    public void doBusiness(Context context) {

        tvCurrentPhone.setText("当前手机号:"+ Constants.getLoginReponse().getPayload().getBindPhone());
    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new BindAccountModule(this)).inject(this);
    }


    @OnClick(R.id.tv_change_bing_phone)
    public void onViewClicked() {
        IntentLauncher.with(this).launch(ChangeBindPhoneInputCodeActivity.class);
//        changeBindPhonePresenter.sendChangePhoneCode(this.bindToLifecycle(),tvMsgCode.getText().toString().trim());
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void dataCountChanged(int count) {

    }

    @Override
    public void loadFinished() {

    }

    @Override
    public void loadFailure() {

    }

    @Override
    public void sendChangePhoneCode(boolean bool) {

    }
}
