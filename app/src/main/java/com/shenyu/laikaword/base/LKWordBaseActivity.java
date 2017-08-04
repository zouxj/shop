package com.shenyu.laikaword.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shenyu.laikaword.interfaces.IBaseActivity;
import com.zxj.utilslibrary.utils.ActivityManageUtil;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public abstract class LKWordBaseActivity extends AppCompatActivity implements IBaseActivity{
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( bindLayout());
        ButterKnife.bind(this);
        ActivityManageUtil.getAppManager().addActivity(this);
        doBusiness(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManageUtil.getAppManager().finishActivity(this);
    }


}
