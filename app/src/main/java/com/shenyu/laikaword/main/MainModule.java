package com.shenyu.laikaword.main;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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
    private final FragmentManager manager;

    public MainModule(FragmentManager manager) {
        this.manager = manager;
    }

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

}

