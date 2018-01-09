package com.zxj.utilslibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.zxj.utilslibrary.AndroidUtilsCore;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

    public class PackageManagerUtil {
    private static final String CHANNEL_KEY = "";//默认的通道
    private static final String CHANNEL_VERSION_KEY = "1.0.3";//默认的通道的版本号
    private static String mChannel;//channel
    private static String mDefaultChannel = "";

        private static final String TAG = PackageManagerUtil.class.getSimpleName();

        /**
         * 获取AndroidManifest中指定的meta-data字符串
         *
         * @return 如果没有获取成功(没有对应值, 或者异常)，则返回值为空
         */
        public static String getStringMetaData(String key) {
            if (TextUtils.isEmpty(key)) {
                return null;
            }
            String resultData = null;
            try {
                PackageManager packageManager = AndroidUtilsCore.getContext().getPackageManager();
                if (packageManager != null) {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(AndroidUtilsCore.getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            resultData = applicationInfo.metaData.getString(key);
                        }
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
                LogUtil.e(TAG, "", e);
            }
            return resultData;
        }

        /**
         * 获取AndroidManifest中指定的meta-data整形值
         *
         * @return 如果没有获取成功(没有对应值, 或者异常)，则返回默认值
         */
        public static int getIntMeta(final String metaName, final int defaultValue) {
            int meta = defaultValue;
            try {
                Context context = AndroidUtilsCore.getContext();
                ApplicationInfo appinfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (appinfo != null) {
                    meta = appinfo.metaData.getInt(metaName, defaultValue);
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "", e);
            }
            return meta;
        }

        /**
         * 获取AndroidManifest中指定版本号整形值
         *
         * @return 如果没有获取成功(没有对应值, 或者异常)，则返回值为-1
         */
        public static int getVersionCode() {
            int verCode = -1;
            Context context = AndroidUtilsCore.getContext();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                if (null != pi.versionName) {
                    verCode = pi.versionCode;
                }
            } catch (PackageManager.NameNotFoundException e) {
                LogUtil.e(TAG, "", e);
            }
            return verCode;
        }
        public static int getVersionCode(Context context) {
            int verCode = -1;
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                if (null != pi.versionName) {
                    verCode = pi.versionCode;
                }
            } catch (PackageManager.NameNotFoundException e) {
                LogUtil.e(TAG, "", e);
            }
            return verCode;
        }
        /**
         * 获取AndroidManifest中指定的版本号名字符串
         *
         * @return 如果没有获取成功(没有对应值, 或者异常)，则返回值为""
         */
        public static String getVersionName(Context context) {
            String verName = "";
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                if (null != pi.versionName) {
                    verName = pi.versionName;
                }
            } catch (PackageManager.NameNotFoundException e) {
                LogUtil.e(TAG, "", e);
            }
            return verName;
        }

        /**
         * 收集设备参数信息,返回json对象
         */
        public static JSONObject collectDeviceInfo() {
            JSONObject result = new JSONObject();
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    result.put(field.getName(), field.get(null).toString());
                } catch (Exception e) {
                    LogUtil.d(TAG, "an error occured when collect device info " + e.getMessage());
                }
            }

            return result;
        }

        /**
         * 程序是否在前台
         */
        public static boolean isAppOnForeground() {
            Context context = AndroidUtilsCore.getContext();
            ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            String packageName = context.getApplicationContext().getPackageName();
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                    // The name of the process that this object is associated with.
                    if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * 退出应用
         */
        public static void exitApp() {
            Context context = AndroidUtilsCore.getContext();
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (context.getPackageName().equals(service.service.getPackageName())) {
                    Intent stopIntent = new Intent();
                    ComponentName serviceCMP = service.service;
                    stopIntent.setComponent(serviceCMP);
                    context.stopService(stopIntent);
                    break;
                }
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

        /**
         * 判断apk是否被安装
         *
         * @return 已安装返回true，未安装返回false
         */
        public static boolean isInstalled(String packageName) {
            boolean result = false;
            try {
                PackageInfo packageInfo = AndroidUtilsCore.getContext().getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                if (packageInfo != null) {
                    result = true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                LogUtil.e(TAG, TAG + "--->Exception:" + e.getMessage());
            }
            return result;
        }

        /**
         * 获取已安装apk版本号
         *
         * @return 指定已安装的包名apk的版本号
         */
        public static int getVersionCode(String packageName) {
            int versionCode = -1;
            if (packageName == null || "".equals(packageName)) {
                return versionCode;
            }
            try {
                PackageInfo info = AndroidUtilsCore.getContext().getPackageManager().getPackageInfo(packageName, 0);
                versionCode = info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                LogUtil.e(TAG, TAG + "--->Exception:" + e.getMessage());
            }
            return versionCode;

        }

        /**
         * 获取手机内部安装的非系统应用 只有基本信息，不包含签名等特殊信息
         *
         * @return 手机内部安装的非系统应用
         */
        public static List<PackageInfo> getInstalledUserApps() {
            PackageManager packageManager = AndroidUtilsCore.getContext().getPackageManager();
            List<PackageInfo> result = new ArrayList<>();
            List<PackageInfo> list = packageManager.getInstalledPackages(0);// 获取手机内所有应用
            for (PackageInfo packageInfo : list) {
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) { // 判断是否为非系统预装的应用程序
                    result.add(packageInfo);
                }
            }
            return result;
        }

        /**
         * 检查当前进程名是否是默认的主进程
         *
         * @return 是否是默认的主进程
         */
        public static boolean checkMainProcess() {
            Context context = AndroidUtilsCore.getContext();
            final String curProcessName = getProcessNameByPid(context, android.os.Process.myPid());
            return isMainProcess(context, curProcessName);
        }

        /**
         * 判断进程是否为主进程
         *
         * @param context     上下文
         * @param processName 进程名称（包名+进程名）
         * @return 是否为主进程
         */
        public static boolean isMainProcess(Context context, String processName) {
            final String mainProcess = context.getApplicationInfo().processName;
            return mainProcess.equals(processName);
        }

        /**
         * 获取当前进程的名称<br/>
         * 通过当前进程id在运行的栈中查找获取进程名称（包名+进程名）
         *
         * @param context 上下文
         * @return 当前进程的名称
         */
        public static String getCurProcessName(Context context) {
            final int curPid = android.os.Process.myPid();
            String curProcessName = getProcessNameByPid(context, curPid);
            if (null == curProcessName) {
                curProcessName = context.getApplicationInfo().processName;
                LogUtil.d(TAG, "getCurProcessName,no find process,curPid=", curPid, ",curProcessName=", curProcessName);
            }
            return curProcessName;
        }

        /**
         * 获取进程的名称<br/>
         * 通过进程id在运行的栈中查找获取进程名称（包名+进程名）
         *
         * @param context 上下文
         * @param pid     指定进程id
         * @return 进程的名称
         */
        public static String getProcessNameByPid(Context context, final int pid) {
            String processName = null;
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                    if (pid == appProcess.pid) {
                        processName = appProcess.processName;
                        break;
                    }
                }
            }
            LogUtil.d(TAG, "getProcessNameByPid,pid=", pid, ",processName=", processName);
            return processName;
        }

        /**
         * 检查当前进程名是否是包含进程名的进程
         *
         * @param context 上下文
         * @return 当前进程名是否是包含进程名的进程
         */
        public static boolean checkTheProcess(final Context context, String endProcessName) {
            ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            int myPid = android.os.Process.myPid();
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                    if (myPid == appProcess.pid) {
                        LogUtil.d(TAG, "process.pid appProcess.processName=" + appProcess.processName + ", endProcessName=" + endProcessName);
                        if (appProcess.processName.endsWith(endProcessName)) {
                            return true;
                        }
                        break;
                    }
                }
            }
            return false;
        }

        /**
         * 获取当前堆栈中的低一个activity
         *
         * @return 当前堆栈中的低一个activity
         */
        @SuppressWarnings("deprecation")
        public static ComponentName getTheProcessBaseActivity() {
            ActivityManager activityManager = (ActivityManager) AndroidUtilsCore.getContext().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.RunningTaskInfo task = activityManager.getRunningTasks(1).get(0);
            if (task.numActivities > 0) {
                LogUtil.d(TAG, "runningActivity topActivity=" + task.topActivity.getClassName());
                LogUtil.d(TAG, "runningActivity baseActivity=" + task.baseActivity.getClassName());
                return task.baseActivity;
            }
            return null;
        }
    public static String getChannel(Context context) {
        //#1、使用第三方工具
//        return WalleChannelReader.getChannel(context.getApplicationContext());
        //由于360要打加固包，之后META-INF数据全部被改，所以取默认channel
        //#2、自己创建工具类
        return getChannel(context, mDefaultChannel);
    }

    /**
     * 返回市场。  如果获取失败返回defaultChannel
     *
     * @param context
     * @param defaultChannel
     * @return
     */
    public static String getChannel(Context context, String defaultChannel) {
        //1、内存中获取
        if (!TextUtils.isEmpty(mChannel)) {
            return mChannel;
        }
        //2、sp中获取
        mChannel = getChannelBySharedPreferences(context);
        if (!TextUtils.isEmpty(mChannel)) {
            return mChannel;
        }
        //3、从apk中获取
        mChannel = getChannelFromApk(context, CHANNEL_KEY);
        if (!TextUtils.isEmpty(mChannel)) {
            //保存sp中备用
            saveChannelBySharedPreferences(context, mChannel);
            return mChannel;
        }
        //全部获取失败
        return defaultChannel;
    }

    /**
     * 从apk中获取版本信息
     *
     * @param context
     * @param channelKey
     * @return
     */
    private static String getChannelFromApk(Context context, String channelKey) {
        //从apk包中获取
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        //默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/" + channelKey;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);//获取压缩包文件
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        String channel = "";
        if (split != null && split.length >= 3) {//此处需要和app本身的包名有关
            channel = split[2];//获取channel名
            //channel = ret.substring(split[0].length() + 1);
        }
        return channel;
    }

    /**
     * 本地保存channel & 对应版本号
     *
     * @param context
     * @param channel
     */
    private static void saveChannelBySharedPreferences(Context context, String channel) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CHANNEL_KEY, channel);
        editor.putInt(CHANNEL_VERSION_KEY, getVersionCode(context));
        editor.apply();
    }

    /**
     * sp---->获取channel
     *
     * @param context
     * @return 为空表示获取异常、sp中的值已经失效、sp中没有此值
     */
    private static String getChannelBySharedPreferences(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int currentVersionCode = getVersionCode(context);
        if (currentVersionCode == -1) {
            //获取错误
            return "";
        }
        int versionCodeSaved = sp.getInt(CHANNEL_VERSION_KEY, -1);
        if (versionCodeSaved == -1) {
            //本地没有存储的channel对应的版本号
            //第一次使用  或者 原先存储版本号异常
            return "";
        }
        if (currentVersionCode != versionCodeSaved) {
            return "";
        }
        return sp.getString(CHANNEL_KEY, "");
    }

    /* 获取渠道名
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */


    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }


            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        return resultData;
    }
    /**是否安装QQ
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}

