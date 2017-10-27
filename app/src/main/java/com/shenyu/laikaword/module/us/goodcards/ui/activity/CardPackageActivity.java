package com.shenyu.laikaword.module.us.goodcards.ui.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.CarPackageViewPagerAdapter;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.di.module.MineModule;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * g购物卡管理
 */
public class CardPackageActivity extends LKWordBaseActivity {


    @BindView(R.id.tb_car_pack)
    TabLayout tbCarPack;
    @BindView(R.id.vp_car_pack)
    ViewPager vpCarPack;
    @Inject
    CarPackageViewPagerAdapter carPackageViewPagerAdapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_card_package;
    }

    @Override
    public void initView() {
        setToolBarTitle("我的卡包");
        tbCarPack.setupWithViewPager(vpCarPack);
    }

    @Override
    public void doBusiness(Context context) {
        initData();

    }

    private void initData(){
        loadViewHelper.showLoadingDialog(this);
        retrofitUtils.addSubscription(RetrofitUtils.apiStores.cardPackage(), new ApiCallback<CarPagerReponse>() {
            @Override
            public void onSuccess(CarPagerReponse model) {
                if (model.isSuccess()) {
                    carPackageViewPagerAdapter.setData(model);
                    vpCarPack.setAdapter(carPackageViewPagerAdapter);
                }
                else
                    loadViewHelper.showEmpty(model.getError().getMessage(),CardPackageActivity.this);


            }

            @Override
            public void onFailure(String msg) {
                loadViewHelper.showErrorResert(CardPackageActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initData();
                    }
                });
            }

            @Override
            public void onFinish() {
                loadViewHelper.closeLoadingDialog();
            }
        });
    }
    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this)).inject(this);
    }

}
