package com.shenyu.laikaword.module.us.goodcards.ui.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.CarPackageViewPagerAdapter;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.di.module.mine.MineModule;
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
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    public int bindLayout() {
        return R.layout.activity_card_package;
    }

    @Override
    public void initView() {
        setToolBarTitle("我的卡包");
        tbCarPack.setupWithViewPager(vpCarPack);
        smartRefreshLayout.setHeaderHeight(45);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {

                loadViewHelper.showLoadingDialog(CardPackageActivity.this);
                RetrofitUtils.getRetrofitUtils().setLifecycleTransformer(CardPackageActivity.this.bindToLifecycle()).addSubscription(RetrofitUtils.apiStores.cardPackage(), new ApiCallback<CarPagerReponse>() {
                    @Override
                    public void onSuccess(CarPagerReponse model) {
                        if (model.isSuccess()){
                            if (model.getPayload()!=null){
                                carPackageViewPagerAdapter.setData(model);
                                vpCarPack.setAdapter(carPackageViewPagerAdapter);
                            }
                        }
                        refreshlayout.finishRefresh();

                    }

                    @Override
                    public void onFailure(String msg) {
                        refreshlayout.finishRefresh();
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
        });
    }

    @Override
    public void doBusiness(Context context) {
        initData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (null!=smartRefreshLayout)
        smartRefreshLayout.autoRefresh();
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
