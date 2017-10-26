package com.shenyu.laikaword.module.mine.appsetting.acountbind;

import android.content.Context;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

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
