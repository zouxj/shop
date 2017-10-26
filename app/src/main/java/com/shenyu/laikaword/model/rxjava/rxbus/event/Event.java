package com.shenyu.laikaword.model.rxjava.rxbus.event;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public class Event {
    public int event;
    public Object object;
    public Event(int event,Object object) {
        this.event = event;
        this.object=object;

    }
}
