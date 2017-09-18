package com.shenyu.laikaword.main;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.shenyu.laikaword.adapter.MainViewPagerAdapter;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.main.activity.MainPageAdapter;
import com.shenyu.laikaword.main.fragment.LeftFragment;
import com.shenyu.laikaword.main.fragment.MainFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

@Module
public class MainModule {
    private  FragmentManager manager;
    private  MainView mainView;
    public MainModule(MainView mainView,FragmentManager fragmentManager){
        this.mainView =mainView;
        this.manager=fragmentManager;
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
        return new MainViewPagerAdapter(manager);
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

