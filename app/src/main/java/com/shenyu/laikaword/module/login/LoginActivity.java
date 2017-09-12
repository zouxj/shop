package com.shenyu.laikaword.module.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.reponse.UserReponse;
import com.shenyu.laikaword.main.activity.MainActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class LoginActivity extends LKWordBaseActivity implements LoginView {
    @Inject
    LoginPresenter loginPresenter;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button)
    Button button;
    private String password;
    private String usename;

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void doBusiness(Context context) {
        editText.setText(loginPresenter.getUserNameFromLocal());
        editText2.setText(loginPresenter.getPasswordFromLocal());
        IntentLauncher.with(this).launch(MainActivity.class);
        RxTextView.textChanges(editText).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                loginPresenter.checkInput(charSequence.toString(), editText.getText().toString());
            }
        });
        RxTextView.textChanges(editText2).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                loginPresenter.checkInput(editText2.getText().toString(), charSequence.toString());
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
            button.setEnabled(true);
            button.setBackgroundColor(Color.GREEN);
        } else {
            button.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public void showUser(UserReponse user) {
        ToastUtil.showToastShort(user.getPassword());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        loginPresenter.login(usename,password);

    }
}
