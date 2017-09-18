package com.shenyu.laikaword.main;

import com.shenyu.laikaword.main.activity.MainActivity;
import com.shenyu.laikaword.main.activity.WelcomePageActivity;
import com.shenyu.laikaword.main.fragment.MainFragment;

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
