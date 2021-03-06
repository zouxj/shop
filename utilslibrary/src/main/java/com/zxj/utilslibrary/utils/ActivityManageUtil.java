package com.zxj.utilslibrary.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Stack;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class ActivityManageUtil {

    private static Stack<Activity> activityStack;
    private static ActivityManageUtil instance;

    private ActivityManageUtil() {
    }

    /**
     * 单一实例
     */
    public static ActivityManageUtil getAppManager() {
        if (instance == null) {
            synchronized (ActivityManageUtil.class){
            if(null==instance){
                instance = new ActivityManageUtil();
                 }
            }

        }
        return instance;
    }

    /**
     * 判断当前Activity是否存在
     *
     */
    public static  boolean IsActivity( Class<?> cls){
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        for (int i = 0 ; i < activityStack.size(); i ++             ) {
            if(cls.equals(activityStack.get(i).getClass())){
                return true;
            }
        }
        return false;
    }
    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }



    public static  void resertApp(){
        Intent intent = UIUtil.getContext().getPackageManager()
                .getLaunchIntentForPackage(UIUtil.getContext().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(UIUtil.getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager)UIUtil.getContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
        System.exit(0);
    }
}
