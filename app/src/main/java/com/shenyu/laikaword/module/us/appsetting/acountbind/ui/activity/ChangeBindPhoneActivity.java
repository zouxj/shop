package com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.module.mine.BindAccountModule;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.appsetting.acountbind.presenter.ChangeBindPhonePresenter;
import com.shenyu.laikaword.module.us.appsetting.acountbind.view.ChangeBindPhoneView;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 更改绑定手机号码
 */
public class ChangeBindPhoneActivity extends LKWordBaseActivity implements ChangeBindPhoneView {


    @BindView(R.id.tv_phome)
    TextView tvPhome;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_current_phone)
    TextView tvCurrentPhone;
    @BindView(R.id.tv_change_bing_phone)
    TextView tvChangeBingPhone;
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
        RxTextView.textChanges(etPhone).subscribe(new Action1<CharSequence>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void call(CharSequence charSequence) {
                tvPhome.setEnabled(StringUtil.validText(charSequence.toString()));
                if (StringUtil.validText(charSequence.toString())) {
                        if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                            tvChangeBingPhone.setBackground(UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle_light));
                    else
                            tvChangeBingPhone.setBackgroundResource(R.drawable.bg_bt_login_rectangle_light);
                } else {
                    if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                        tvChangeBingPhone.setBackground(UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle));
                    else
                        tvChangeBingPhone.setBackgroundResource(R.drawable.bg_bt_login_rectangle);
                }
            }
        });
    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new BindAccountModule(this)).inject(this);
    }


    @OnClick(R.id.tv_change_bing_phone)
    public void onViewClicked() {
        changeBindPhonePresenter.checkPhone(this.bindToLifecycle(),etPhone.getText().toString());


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
    public void checkPhone(boolean bool, String msg) {
            if (bool)
                IntentLauncher.with(this).put("phone",etPhone.getText().toString()).launch(ChangeBindPhoneInputCodeActivity.class);
            else {
                etPhone.setText("");
                ToastUtil.showToastShort(msg);
            }
    }
}
