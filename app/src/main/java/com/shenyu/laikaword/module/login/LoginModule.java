package com.shenyu.laikaword.module.login;

import android.app.Activity;

import com.shenyu.laikaword.interfaces.BaseUiListener;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

@Module
public class LoginModule {
    private final  LoginView loginView;
    private Activity activity;
    public LoginModule(Activity activity,LoginView loginView ,BaseUiListener loginListener){
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
