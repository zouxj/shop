package com.shenyu.laikaword.module.home.ui.activity;

import android.content.Context;


import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.module.home.view.GoodsDetailsView;
import com.shenyu.laikaword.module.launch.LaiKaApplication;

public class GoodsDetailsActivity extends LKWordBaseActivity implements GoodsDetailsView {


    @Override
    public int bindLayout() {
        return R.layout.activity_goods_details;
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(
                new MainModule(this, this)).inject(this);
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void loadSucceed(BaseReponse baseReponse) {

    }

    @Override
    public void loadFailure() {

    }
}