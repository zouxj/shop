package com.shenyu.laikaword.di;

import com.shenyu.laikaword.main.MainComponent;
import com.shenyu.laikaword.main.MainModule;
import com.shenyu.laikaword.module.login.LoginComponent;
import com.shenyu.laikaword.module.login.LoginModule;
import com.shenyu.laikaword.module.mine.MineComponent;
import com.shenyu.laikaword.module.mine.MineModule;
import com.shenyu.laikaword.module.shop.ShopCommponent;
import com.shenyu.laikaword.module.shop.ShopModule;

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
