package com.shenyu.laikaword.Interactor;

import android.content.Context;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface  IBaseActivity {
    /**
     * 绑定渲染视图的布局文件
     *
     * @return 布局文件资源id
     */
     int bindLayout();

    /**
     * 业务处理操作（onCreate方法中被调用）<br/>
     * 数据逻辑及业务操作在此方法中<br/>
     *
     *            当前Activity对象上下文
     */
     void doBusiness(Context context);

    void setupActivityComponent();
}
