package com.shenyu.laikaword.di.component;

import com.shenyu.laikaword.di.module.LoginModule;
import com.shenyu.laikaword.module.login.ui.activity.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {
    LoginActivity inject(LoginActivity loginActivity);
}