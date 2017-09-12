package com.shenyu.laikaword.module.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

@Module
public class LoginModule {
    private final  LoginView loginView;
    public LoginModule(LoginView loginView){
        this.loginView = loginView;
    }
    @Provides
    LoginView provideLoginView() {
        return loginView;
    }
    @Provides
    LoginPresenter provideLoginPresenter() {
        return new LoginPresenter(loginView);
    }
}
