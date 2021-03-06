package com.shenyu.laikaword.di.module.mine;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.model.adapter.CarPackageViewPagerAdapter;
import com.shenyu.laikaword.model.adapter.PurchaseViewPagerAdapter;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.model.adapter.ZhuanMaiViewAdapter;
import com.shenyu.laikaword.module.us.appsetting.UserInfoPresenter;
import com.shenyu.laikaword.module.us.appsetting.UserInfoView;
import com.shenyu.laikaword.module.us.resell.presenter.CommitResellPresenter;
import com.shenyu.laikaword.module.us.resell.presenter.ResellInputCodePresenter;
import com.shenyu.laikaword.module.us.resell.view.CommitResellView;
import com.shenyu.laikaword.module.us.resell.view.ResellInputCodeView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shenyu_zxjCode on 2017/9/13 0013.
 */
@Module
public class MineModule {
    private Context mContext;
    private BaseLoadView baseLoadView;
    private Activity mActivity;
    private FragmentManager fm;


    public MineModule(Activity activity, BaseLoadView userInfoView){
        this.mActivity =activity;
        this.baseLoadView=userInfoView;
    }
    public MineModule(Activity activity){
        this.mActivity =activity;
    }

    public MineModule(FragmentManager fm){
        this.fm= fm;
    }
    @Provides
    FragmentTransaction provideFragmentManger(){
        return fm.beginTransaction();
    }

    @Provides
    CityDataHelper provideCityDataHelper(){
        return new CityDataHelper(mContext,3);
    }

    @Provides
    UserInfoPresenter provideUserInfoPresenter(){
        return new UserInfoPresenter(mActivity,(UserInfoView) baseLoadView);
    }
    @Provides
    CarPackageViewPagerAdapter provideCarPackageViewPagerAdapter(){
     return new CarPackageViewPagerAdapter(mActivity);
    }
    @Provides
    PurchaseViewPagerAdapter providePurchaseViewPagerAdapter(){
        return    new PurchaseViewPagerAdapter(fm);
    }
    @Provides
    ZhuanMaiViewAdapter provideZhuanMaiViewAdapter(){
        return    new ZhuanMaiViewAdapter(fm);
    }
    @Provides
    ResellInputCodePresenter provideResellInputCodePresenter(){
        return    new ResellInputCodePresenter((ResellInputCodeView) baseLoadView);
    }

    @Provides
    CommitResellPresenter provideCommitResellPresenter(){
        return    new CommitResellPresenter((CommitResellView) baseLoadView);
    }
}
