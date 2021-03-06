package com.shenyu.laikaword.model.rxjava.rxbus;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public class RxSubscriptions {
    private static CompositeSubscription mSubscriptions = new CompositeSubscription();



    public static boolean isUnsubscribed() {

        return mSubscriptions.isUnsubscribed();

    }



    public static void add(Subscription s) {

        if (s != null) {

            mSubscriptions.add(s);

        }

    }



    public static void remove(Subscription s) {
        if (s != null) {
            mSubscriptions.remove(s);

        }

    }



    public static void clear() {

        mSubscriptions.clear();

    }



    public static void unsubscribe() {

        mSubscriptions.unsubscribe();

    }



    public static boolean hasSubscriptions() {

        return mSubscriptions.hasSubscriptions();

    }
}
