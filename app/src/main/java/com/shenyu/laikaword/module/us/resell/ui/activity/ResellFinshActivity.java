package com.shenyu.laikaword.module.us.resell.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.module.us.resell.presenter.ResellInputCodePresenter;
import com.zxj.utilslibrary.utils.IntentLauncher;

import butterknife.OnClick;

public class ResellFinshActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {

        return R.layout.activity_resell_finsh;
    }

    @Override
    public void initView() {
        setToolBarTitle("转卖发布完成");
        leftTitle.setVisibility(View.GONE);
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }
    @OnClick({R.id.tv_jd,R.id.tv_jx})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_jd:
                //TODO 进度
                IntentLauncher.with(this).launchFinishCpresent(ResellActivity.class);
                break;
            case R.id.tv_jx:
                //TODO 继续
                IntentLauncher.with(this).launchFinishCpresent(ResellInputCodePresenter.class);
                break;
        }
    }
}
