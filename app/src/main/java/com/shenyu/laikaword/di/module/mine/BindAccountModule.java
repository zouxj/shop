package com.shenyu.laikaword.di.module.mine;

import com.shenyu.laikaword.module.us.appsetting.acountbind.presenter.ChangeBindPhoneInputCodePresent;
import com.shenyu.laikaword.module.us.appsetting.acountbind.presenter.ChangeBindPhonePresenter;
import com.shenyu.laikaword.module.us.appsetting.acountbind.view.ChangeBindPhoneInputCodeView;
import com.shenyu.laikaword.module.us.appsetting.acountbind.view.ChangeBindPhoneView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shenyu_zxjCode on 2017/12/29 0029.
 */
@Module
public class BindAccountModule {

    private ChangeBindPhoneView changeBindPhoneView;
    private ChangeBindPhoneInputCodeView changeBindPhoneInputCodeView;

    public BindAccountModule(ChangeBindPhoneView changeBindPhoneView){
        this.changeBindPhoneView=changeBindPhoneView;
    }
    public BindAccountModule(ChangeBindPhoneInputCodeView changeBindPhoneInputCodeView){
        this.changeBindPhoneInputCodeView=changeBindPhoneInputCodeView;
    }
    @Provides
    ChangeBindPhonePresenter provideChangeBindPhonePresenter(){
        return   new ChangeBindPhonePresenter(changeBindPhoneView);
    }

    @Provides
    ChangeBindPhoneInputCodePresent provideChangeBindPhoneInputCodePresent(){
        return   new ChangeBindPhoneInputCodePresent(changeBindPhoneInputCodeView);
    }
}
