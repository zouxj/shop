package com.shenyu.laikaword.module.mine.systemsetting.activity;

import android.content.Context;
import android.os.Bundle;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.main.activity.MainActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AcountBdingSuccessActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_acount_bding;
    }

    @Override
    public void initView() {
        setToolBarTitle("绑定成功");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }



    @OnClick(R.id.tv_back_main)
    public void onViewClicked() {
        IntentLauncher.with(this).launch(MainActivity.class);
        finish();
    }
}
