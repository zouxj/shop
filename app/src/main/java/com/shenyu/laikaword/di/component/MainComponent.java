package com.shenyu.laikaword.di.component;

import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.shenyu.laikaword.module.home.ui.fragment.MainFragment;
import com.shenyu.laikaword.module.launch.StartActivity;
import com.shenyu.laikaword.module.launch.WelcomePageActivity;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {
     MainActivity inject(MainActivity loginActivity);
     MainFragment inject(MainFragment mainFragment);
    WelcomePageActivity inject(WelcomePageActivity welcomePageActivity);
}
