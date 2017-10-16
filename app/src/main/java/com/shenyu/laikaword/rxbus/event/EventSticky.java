package com.shenyu.laikaword.rxbus.event;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public class EventSticky {
    public String event;
    public EventSticky(String event) {
        this.event = event;
    }
    @Override
    public String toString() {
        return "EventSticky{" +
                "event='" + event + '\'' + '}';

    }
}
