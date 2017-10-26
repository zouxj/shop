package com.shenyu.laikaword.di.component;

import com.shenyu.laikaword.di.module.AppModule;
import com.shenyu.laikaword.di.module.ApiModule;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.di.module.LoginModule;
import com.shenyu.laikaword.di.module.MineModule;
import com.shenyu.laikaword.di.module.ShopModule;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Administrator on 2017/8/7 0007.
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    LoginComponent plus(LoginModule loginModule);
    MainComponent plus(MainModule mainModule);
    MineComponent plus(MineModule mineModule);
    ShopCommponent plus(ShopModule mineModule);
}
