package com.shenyu.laikaword.di.module;



import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.shenyu.laikaword.model.adapter.MainViewPagerAdapter;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.module.home.view.MainView;
import com.shenyu.laikaword.module.home.ui.fragment.LeftFragment;
import com.shenyu.laikaword.module.home.presenter.MainPresenter;
import com.shenyu.laikaword.module.home.ui.fragment.MainFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

@Module
public class MainModule {
    private  FragmentManager manager;
    private MainView mainView;
    private Activity activity;
   private final static  String[] mlist  = new String[]{"移动卡", "京东卡", "联通卡", "电信卡"};
    public MainModule(MainView mainView, Activity activity){
        this.mainView =mainView;
        this.activity=activity;
    }
    public MainModule(FragmentManager manager) {
        this.manager = manager;
    }
    public MainModule(){}
    @Provides
    FragmentTransaction provideLoginPresenter() {
       return manager.beginTransaction();
    }
    @Provides
    FragmentManager provideFragmentManager() {
        return manager;
    }
    @Provides
    LeftFragment provideLeftFragment(){
        return new LeftFragment();
    }
    @Provides
    MainFragment provideMainFragment(){
        return new MainFragment();
    }
    @Provides
    MainViewPagerAdapter provideMainViewPagerAdapter(){
        return new MainViewPagerAdapter(activity,mlist);
    }

    /**
     * 实例化MainPresenter
     * @return
     */
    @Provides
    MainPresenter provideMainPresenter(){
        return new MainPresenter(mainView);
    }

    @Provides
    BannerHelper provideBannerHelper(){
        return BannerHelper.getInstance();
    }

}

