package com.shenyu.laikaword.module.mine.systemsetting;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;

public class UpdateUserNameActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_update_user_name;
    }

    @Override
    public void initView() {
        setToolBarRight("修改昵称");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }
}
