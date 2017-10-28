package com.zxj.utilslibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class IntentLauncher {

    private IntentLauncher() {
    }

    private static WeakReference<Activity> weakReference;
    private volatile static Intent mIntent = null;

    public static IntentLauncher with(Activity sourceContext) {
        weakReference = new WeakReference<>(sourceContext);
        mIntent = new Intent();
        return new IntentLauncher();
    }

    public IntentLauncher put(String key, Object value) {
        if (value instanceof String) {
            mIntent.putExtra(key, (String) value);
        } else if (value instanceof Integer) {
            mIntent.putExtra(key, (int) value);
        } else if (value instanceof Double) {
            mIntent.putExtra(key, (double) value);
        } else if (value instanceof Float) {
            mIntent.putExtra(key, (float) value);
        } else if (value instanceof Boolean) {
            mIntent.putExtra(key, (boolean) value);
        } else if (value instanceof CharSequence) {
            mIntent.putExtra(key, (CharSequence) value);
        } else if (value instanceof Long) {
            mIntent.putExtra(key, (long) value);
        } else if (value instanceof Parcelable) {
            mIntent.putExtra(key, (Parcelable) value);
        } else {
            throw new IllegalArgumentException("当前传入的数据类型暂未添加，请先添加.");
        }
        return this;
    }

    public IntentLauncher putListInt(String key, ArrayList<Integer> value) {
        mIntent.putIntegerArrayListExtra(key, value);
        return this;
    }

    public IntentLauncher putListChar(String key, ArrayList<CharSequence> value) {
        mIntent.putCharSequenceArrayListExtra(key, value);
        return this;
    }

    public IntentLauncher putListString(String key, ArrayList<String> value) {
        mIntent.putStringArrayListExtra(key, value);
        return this;
    }
    public IntentLauncher putObjectString(String key, Object value) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, (Serializable) value);
        mIntent.putExtras(bundle);
        return this;
    }

    public IntentLauncher putListParcelable(String key, ArrayList<? extends Parcelable> value) {
        mIntent.putParcelableArrayListExtra(key, value);
        return this;
    }

    public void launch(Class<?> cls) {
        if (weakReference != null && weakReference.get() != null) {
            Context context = weakReference.get();
            mIntent.setClass(context, cls);
            context.startActivity(mIntent);
            weakReference.clear();
            weakReference = null;
            mIntent = null;
        }
    }
    public void launchViews(String url) {
        if (weakReference != null && weakReference.get() != null) {
            Context context = weakReference.get();
            mIntent.setAction("android.intent.action.VIEW");
            mIntent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
            Uri content_url = Uri.parse(url);
            mIntent.setData(content_url);
            context.startActivity(mIntent);
            weakReference.clear();
            weakReference = null;
            mIntent = null;
        }
    }
    public void launchFinishCpresent(Class<?> cls) {
        if (weakReference != null && weakReference.get() != null) {
            Context context = weakReference.get();
            mIntent.setClass(context, cls);
            context.startActivity(mIntent);
            weakReference.get().finish();
            weakReference.clear();
            weakReference = null;
            mIntent = null;
        }
    }
}
