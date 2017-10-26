package com.shenyu.laikaword.di.module;

import android.app.Activity;

import com.shenyu.laikaword.module.login.view.LoginView;
import com.shenyu.laikaword.module.login.presenter.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

@Module
public class LoginModule {
    private final LoginView loginView;
    private Activity activity;
    public LoginModule(Activity activity,LoginView loginView ){
        this.loginView = loginView;
        this.activity=activity;
    }
    @Provides
    LoginView provideLoginView() {
        return loginView;
    }
    @Provides
    LoginPresenter provideLoginPresenter() {
        return new LoginPresenter(activity,loginView);
    }

}
