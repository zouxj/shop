package com.shenyu.laikaword.module.shop.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.main.activity.MainActivity;
import com.shenyu.laikaword.module.mine.remaining.PurchaseCardActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickUpSuccessActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up_success;
    }

    @Override
    public void doBusiness(Context context) {
        setToolBarTitle("申请完成");

    }


    @Override
    public void setupActivityComponent() {

    }

    @OnClick({R.id.tv_sq_check_state, R.id.tv_sq_back_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sq_check_state:
                //TODO 查看申请状态
                IntentLauncher.with(this).launch(PurchaseCardActivity.class);
                finish();
                break;
            case R.id.tv_sq_back_main:
                //TODO 返回首页
                IntentLauncher.with(this).launch(MainActivity.class);
                finish();
                break;
        }
    }
}
