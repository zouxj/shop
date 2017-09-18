package com.shenyu.laikaword.module.mine.systemsetting;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;

/**
 * 关于我们
 */
public class AboutAppActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_about_app;
    }

    @Override
    public void initView() {
        setToolBarTitle("关于我们");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }
}
