package com.shenyu.laikaword;

import android.app.Application;

import com.zxj.utilslibrary.AndroidUtilsCore;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class LaiKaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidUtilsCore.install(this);
    }
}
