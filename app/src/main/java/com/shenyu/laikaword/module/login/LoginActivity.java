package com.shenyu.laikaword.module.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.reponse.UserReponse;
import com.zxj.utilslibrary.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 用户登录页面
 */
public class LoginActivity extends LKWordBaseActivity implements LoginView {


    @BindView(R.id.et_use_phone)
    EditText etUsePhone;
    @BindView(R.id.et_user_msg_code)
    EditText etUserMsgCode;
    @BindView(R.id.tv_send_msg_code)
    TextView tvSendMsgCode;
    @BindView(R.id.bt_login)
    Button btLogin;
    @Inject
    LoginPresenter loginPresenter;
    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void doBusiness(Context context) {
        RxTextView.textChanges(etUsePhone).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                loginPresenter.checkInput(charSequence.toString(), etUserMsgCode.getText().toString());
            }
        });
        RxTextView.textChanges(etUserMsgCode).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                loginPresenter.checkInput(etUsePhone.getText().toString(), charSequence.toString());
            }
        });
    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new LoginModule(this)).inject(this);
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
    public void canLogin(boolean canLogin) {
        if (canLogin) {
            btLogin.setEnabled(true);
            btLogin.setBackgroundColor(Color.GREEN);
        } else {
            btLogin.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public void showUser(UserReponse user) {
        ToastUtil.showToastShort(user.getPassword());
    }


    @OnClick({R.id.bt_login, R.id.tv_send_msg_code,R.id.tv_qq_login,R.id.tv_weixin_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                loginPresenter.login(etUsePhone.getText().toString().trim(), tvSendMsgCode.getText().toString().trim());
                break;
            case R.id.tv_send_msg_code:
                String phone = etUsePhone.getText().toString().trim();
                loginPresenter.sendMsg(phone, tvSendMsgCode);
                break;
            case R.id.tv_qq_login:
                loginPresenter.loginQQ();
                break;
            case R.id.tv_weixin_login:
                loginPresenter.loginWx();
                break;
        }

    }

}
