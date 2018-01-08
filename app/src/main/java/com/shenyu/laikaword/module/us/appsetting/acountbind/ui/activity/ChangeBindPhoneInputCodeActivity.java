package com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity;

import android.content.Context;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.mine.BindAccountModule;
import com.shenyu.laikaword.helper.SendMsgHelper;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.appsetting.acountbind.presenter.ChangeBindPhoneInputCodePresent;
import com.shenyu.laikaword.module.us.appsetting.acountbind.view.ChangeBindPhoneInputCodeView;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.widget.countdownview.PayPsdInputView;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;

public class ChangeBindPhoneInputCodeActivity extends LKWordBaseActivity  implements ChangeBindPhoneInputCodeView{

    @BindView(R.id.psd_view_password)
    PayPsdInputView etInputPassWord;
    @BindView(R.id.tv_send_msg_code)
    TextView sendMsgCode;
    @BindView(R.id.tv_current_phone)
    TextView tCurentPhone;
    @Inject
    ChangeBindPhoneInputCodePresent changeBindPhoneInputCodePresent;
    @Override
    public int bindLayout() {
        return R.layout.activity_change_bind_phone_input_code;
    }

    @Override
    public void doBusiness(Context context) {
        KeyBoardUtil.showSoftInput(etInputPassWord);
    final String phone = getIntent().getStringExtra("phone");
        tCurentPhone.setText("当前手机号码:"+phone);
        SendMsgHelper.sendMsg(this.bindToLifecycle(),sendMsgCode,phone,"changePhone");
        etInputPassWord.setOnInputPasswordListener(new PayPsdInputView.onInputPasswordListener() {
        @Override
        public void onInputListner(String code) {
            changeBindPhoneInputCodePresent.sendChangePhoneCode(ChangeBindPhoneInputCodeActivity.this.bindToLifecycle(),phone,code);
        }
    });
}

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new BindAccountModule(this)).inject(this);
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
    public void verifyMsgCode(boolean bool,String msg) {
        if (bool) {
            ToastUtil.showToastShort("修改成功");
            refreshUser();
            IntentLauncher.with(ChangeBindPhoneInputCodeActivity.this).launchFinishCpresent(MainActivity.class);
        } else {
        ToastUtil.showToastShort(msg);
    }
    }
}
