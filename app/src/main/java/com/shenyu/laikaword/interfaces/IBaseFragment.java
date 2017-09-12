package com.shenyu.laikaword.interfaces;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface IBaseFragment {
    /**
     * 绑定渲染视图的布局文件
     *
     * @return 布局文件资源id
     */
     int bindLayout();
    /**
     * 业务处理操作（onViewCreated方法中调用）
     *
     * @param mContext
     *            当前Activity对象上下文
     */
     void doBusiness();

    /**
     * 用来注解链接
     */
    void setupFragmentComponent();
}
