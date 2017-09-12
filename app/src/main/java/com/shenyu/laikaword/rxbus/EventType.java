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
