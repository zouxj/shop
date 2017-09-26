package com.shenyu.laikaword.module.shop;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.module.shop.activity.ConfirmOrderPresenter;
import com.shenyu.laikaword.module.shop.activity.ConfirmOrderView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */

@Module
public class ShopModule {
    private Activity mActivity;
    private ConfirmOrderView mConfirmOrderView;

    public ShopModule(Activity activity,ConfirmOrderView confirmOrderView ){
        this.mActivity=activity;
        this.mConfirmOrderView=confirmOrderView;
    }
    @Provides
    public RecycleViewDivider provideecycleViewDividerR(){
        return new RecycleViewDivider(mActivity,  LinearLayoutManager.HORIZONTAL, R.drawable.revycle_divider_shap);
    }
    @Provides
    public LinearLayoutManager provideLinearLayoutManager(){
        return  new LinearLayoutManager(mActivity);
    }
    @Provides
    public ConfirmOrderPresenter provideConfirmOrderPresenter(){
        return  new ConfirmOrderPresenter(mActivity,mConfirmOrderView);
    }



}
