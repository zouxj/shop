package com.shenyu.laikaword.module.login;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {
    LoginActivity inject(LoginActivity loginActivity);
}