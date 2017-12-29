package com.shenyu.laikaword.di.module.mine;

import com.shenyu.laikaword.model.adapter.ZhuanMaiViewAdapter;
import com.shenyu.laikaword.module.us.appsetting.acountbind.presenter.ChangeBindPhonePresenter;
import com.shenyu.laikaword.module.us.appsetting.acountbind.view.ChangeBindPhoneView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shenyu_zxjCode on 2017/12/29 0029.
 */
@Module
public class BindAccountModule {

    private ChangeBindPhoneView changeBindPhoneView;

    public BindAccountModule(ChangeBindPhoneView changeBindPhoneView){
        this.changeBindPhoneView=changeBindPhoneView;
    }
    @Provides
    ChangeBindPhonePresenter provideChangeBindPhonePresenter(){
        return   new ChangeBindPhonePresenter(changeBindPhoneView);
    }
}
