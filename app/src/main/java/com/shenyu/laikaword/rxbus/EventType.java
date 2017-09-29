package com.shenyu.laikaword.rxbus;

import android.view.View;

/**
 * Created by Administrator on 2017/9/8 0008.
 * 事件类型
 */

public class EventType {
    /**
     * 打开左边菜单的页面
     */
    public static final int ACTION_OPONE_LEFT=0x01;
    /**
     * 上拉加载更多
     */
    public static final int ACTION_LODE_MORE=0x02;
    public static final int ACTION_PULL_REFRESH=0x03;
    public static final int ACTION_MAIN_SETDATE=0x04;
    public int action;
    public Object object;
    public View view;
    public EventType(int i, Object obj) {
        this.action = i;
        this.object=obj;
    }
    public EventType(int i,View view){
        this.action=i;
        this.view=view;
    }
}
