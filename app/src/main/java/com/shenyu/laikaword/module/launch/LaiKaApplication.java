package com.shenyu.laikaword.module.launch;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.component.AppComponent;
import com.shenyu.laikaword.di.component.DaggerAppComponent;
import com.shenyu.laikaword.di.module.AppModule;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.zxj.utilslibrary.AndroidUtilsCore;
import com.zxj.utilslibrary.utils.ActivityManageUtil;
import com.zxj.utilslibrary.utils.DeviceInfo;
import com.zxj.utilslibrary.utils.PackageManagerUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class LaiKaApplication extends MultiDexApplication {
    private   AppComponent appComponent;
    public static IWXAPI iwxapi;
    public static Tencent mTencent;
    @Override
    public void onCreate() {

        super.onCreate();
        AndroidUtilsCore.install(this);
        initSdk();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
    public  AppComponent getAppComponent(){
        return appComponent;
    }
    public static LaiKaApplication get(Context context){
        return (LaiKaApplication) context.getApplicationContext();
    }

    /**
     * 初始化极光
     */
    public void initSdk(){
        //极光配置
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //Buglly配置
//        CrashReport.initCrashReport(getApplicationContext(), "1ab960f148", false);
//        CrashReport.testJavaCrash();
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
//...在这里设置strategy的属性，在bugly初始化时传入
        strategy.setAppVersion(PackageManagerUtil.getVersionCode()+"");      //App的版本
        strategy.setAppPackageName("com.shenyu.laikaword");  //App的包名
        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            public Map<String, String> onCrashHandleStart(int crashType, String errorType,
                                                          String errorMessage, String errorStack) {
                LinkedHashMap<String, String> map = new LinkedHashMap();
                map.put("Key", "Value");
                return map;
            }

            @Override
            public byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType,
                                                           String errorMessage, String errorStack) {
                try {
                    return "Extra data.".getBytes("UTF-8");
                } catch (Exception e) {
                    return null;
                }
            }

        });
        CrashReport.initCrashReport(this, "1ab960f148", true, strategy);

        //leak配置
        //微信登录
         iwxapi = WXAPIFactory.createWXAPI(this, Constants.WX_APPID,false);
        iwxapi.registerApp(Constants.WX_APPID);
        //腾讯登录
        mTencent = Tencent.createInstance(Constants.QQ_APPID, this);


    }

    /**
     * 初始化hotFix
     */
    private void iniHotFix(){
        // initialize最好放在attachBaseContext最前面
        SophixManager.getInstance().setContext(this)
                .setAppVersion(PackageManagerUtil.getVersionCode(this)+"")
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀，以此加快应用补丁
                            // 建议调用killProcessSafely，详见1.3.2.3
                           ActivityManageUtil.resertApp();
                             SophixManager.getInstance().killProcessSafely();
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                             SophixManager.getInstance().cleanPatches();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        iniHotFix();
        MultiDex.install(base);

    }
}
