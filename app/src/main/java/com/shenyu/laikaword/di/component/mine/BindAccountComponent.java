package com.shenyu.laikaword.di.component.mine;

import com.shenyu.laikaword.di.module.mine.BindAccountModule;
import com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity.ChangeBindPhoneActivity;
import com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity.ChangeBindPhoneInputCodeActivity;

import dagger.Subcomponent;

/**
 * Created by shenyu_zxjCode on 2017/12/29 0029.
 */
@Subcomponent(modules={BindAccountModule.class})
public interface BindAccountComponent {
    ChangeBindPhoneActivity inject(ChangeBindPhoneActivity changeBindPhoneActivity);
    ChangeBindPhoneInputCodeActivity inject(ChangeBindPhoneInputCodeActivity changeBindPhoneInputCodeActivity);

}
