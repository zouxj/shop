package com.shenyu.laikaword.module.mine.systemsetting.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;

/**
 * 更换手机号
 */
public class ChangePhoneActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initView() {
        setToolBarTitle("更换手机号码");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }
}
