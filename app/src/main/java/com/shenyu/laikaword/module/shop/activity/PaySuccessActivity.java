package com.shenyu.laikaword.module.shop.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.main.activity.MainActivity;
import com.shenyu.laikaword.module.mine.cards.activity.CardPackageActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.Observable;

import butterknife.OnClick;

public class PaySuccessActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initView() {
        setToolBarTitle("支付完成");
    }

    @OnClick(R.id.tv_back_main)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_back_main:
                IntentLauncher.with(this).launch(MainActivity.class);
                finish();
                break;
        }
    }
    @Override
    public void doBusiness(Context context) {
        UIUtil.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentLauncher.with(PaySuccessActivity.this).launch(CardPackageActivity.class);
            }
        },3000);
    }

    @Override
    public void setupActivityComponent() {

    }
}
