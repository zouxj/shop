package com.shenyu.laikaword.module.us.setpassword;

import android.content.Context;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.widget.countdownview.PayPsdInputView;

import butterknife.BindView;

/**
 * 设置支付密码第一步
 */
public class SetPassWordOneActivity extends LKWordBaseActivity {

    @BindView(R.id.psd_view_password)
    PayPsdInputView psdViewPassword;

    String typeActivity;
    @Override
    public int bindLayout() {
        return R.layout.activity_set_pass_word_one;
    }

    @Override
    public void initView() {
        typeActivity = getIntent().getStringExtra("typeActivity");
        if (typeActivity!=null&&typeActivity.equals("RESERT"))
            setToolBarTitle("重置支付密码");
        else
            setToolBarTitle("设置支付密码");
    }

    @Override
    public void doBusiness(Context context) {
        KeyBoardUtil.showSoftInput(psdViewPassword);
        psdViewPassword.setOnInputPasswordListener(new PayPsdInputView.onInputPasswordListener() {
            @Override
            public void onInputListner(String str) {
                //TODO 设置密码
                String codeToken = getIntent().getStringExtra("codeToken");
                if (typeActivity!=null&&typeActivity.equals("RESERT")) {
                    IntentLauncher.with(SetPassWordOneActivity.this).put("typeActivity", typeActivity).put("codeToken", codeToken).put("PAYPASSWORD", str).launchFinishCpresent(SetPassWordTwoActivity.class);
                finish();
                }  else {
                    IntentLauncher.with(SetPassWordOneActivity.this).put("codeToken", codeToken).put("PAYPASSWORD", str).launchFinishCpresent(SetPassWordTwoActivity.class);
                    finish();
                }

            }
        });

    }

    @Override
    public void setupActivityComponent() {

    }
}
