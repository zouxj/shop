package com.shenyu.laikaword.di.component;

import com.shenyu.laikaword.di.component.mine.BindAccountComponent;
import com.shenyu.laikaword.di.module.AppModule;
import com.shenyu.laikaword.di.module.BankModule;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.di.module.LoginModule;
import com.shenyu.laikaword.di.module.mine.MineModule;
import com.shenyu.laikaword.di.module.ShopModule;
import com.shenyu.laikaword.di.module.mine.BindAccountModule;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Administrator on 2017/8/7 0007.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    LoginComponent plus(LoginModule loginModule);
    MainComponent plus(MainModule mainModule);
    MineComponent plus(MineModule mineModule);
    ShopCommponent plus(ShopModule mineModule);
    BankComponet plus(BankModule bankModule);
    BindAccountComponent plus(BindAccountModule bankModule);
}
