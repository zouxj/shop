package com.shenyu.laikaword.module.mine.message;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.widget.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserMessageActivity extends LKWordBaseActivity {


    @BindView(R.id.lv_load_state)
    LoadingView lvLoadState;

    @Override
    public int bindLayout() {
        return R.layout.activity_user_message;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView() {
        setToolBarTitle("通知消息");
        lvLoadState.setStatue(LoadingView.NO_DATA);
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }

}
