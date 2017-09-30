package com.shenyu.laikaword;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.AppComponent;
import com.shenyu.laikaword.di.AppModule;
import com.shenyu.laikaword.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.zxj.utilslibrary.AndroidUtilsCore;
import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import cn.jpush.android.api.JPushInterface;

import static com.shenyu.laikaword.jpushreceiver.TagAliasOperatorHelper.ACTION_SET;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class LaiKaApplication extends Application {
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
        CrashReport.initCrashReport(getApplicationContext(), "1ab960f148", false);
//        CrashReport.testJavaCrash();
        //leak配置
        LeakCanary.install(this);
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
