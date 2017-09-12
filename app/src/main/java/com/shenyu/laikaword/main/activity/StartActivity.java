package com.shenyu.laikaword.main.activity;

import android.content.Context;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.reponse.UserReponse;
import com.shenyu.laikaword.http.NetWorks;
import com.shenyu.laikaword.http.uitls.SimpleCallback;
import com.shenyu.laikaword.module.login.LoginActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;

/**
 * 启动页
 */
public class StartActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_start;
    }
    String usename;
    String password;
    @Override
    public void doBusiness(Context context) {
         usename = SPUtil.getString("usename","");
         password =SPUtil.getString("password","");
        if (StringUtil.validText(SPUtil.getString("start_app",""))){
            //TODO 第一次登录
            IntentLauncher.with(this).launch(WelcomePageActivity.class);
            SPUtil.putString("start_aa","one");
            finish();
        }else {
            if(StringUtil.validText(usename)&&StringUtil.validText(password)){
                    //TODO 登录进入APP
                login();
            }else{
                //TODO 没有登录过直接到登录的页面
//                IntentLauncher.with(this).launch(TestMainActivity.class);
                IntentLauncher.with(this).launch(LoginActivity.class);
//                IntentLauncher.with(this).launch(WelcomePageActivity.class);//Test
            }

        }
    }

    @Override
    public void setupActivityComponent() {

    }
    public void login(){
        NetWorks.loginUser(usename, password, new SimpleCallback<UserReponse>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(UserReponse userReponse) {
                IntentLauncher.with(mActivity).launch(TestMainActivity.class);
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
