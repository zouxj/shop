package com.shenyu.laikaword.module.login.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.model.net.retrofit.ErrorCode;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.SendMsgHelper;
import com.shenyu.laikaword.Interactor.BaseUiListener;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.module.login.view.LoginView;
import com.shenyu.laikaword.module.us.appsetting.acountbind.ui.activity.BoundPhoneActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.analytics.MobclickAgent;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.UUID;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    private String scope; //获取信息的范围参数
    private Activity mActivity;
    public LoginPresenter(Activity activity, LoginView loginView) {
        this.mvpView = loginView;
        this.mActivity=activity;
        attachView(mvpView);
       LoginReponse loginReponse = Constants.getLoginReponse();
       if (null!=loginReponse)
           mvpView.showUser(loginReponse);
    }



    public void login(LifecycleTransformer lifecycleTransformer,String phone,String code){
        addSubscription(lifecycleTransformer,apiStores.loginPhone(phone,code), new ApiCallback<LoginReponse>() {
            @Override
            public void onSuccess(LoginReponse model) {
                if (model.isSuccess()){
                    //TODO 登录成功
                    MobclickAgent.onProfileSignIn(model.getPayload().getBindPhone());
                    SPUtil.putString(Constants.TOKEN,model.getPayload().getToken());
                    SPUtil.saveObject(Constants.LOGININFO_KEY,model);
                    ToastUtil.showToastShort("登录成功");
                    IntentLauncher.with(mActivity).launchFinishCpresent(MainActivity.class);

                }else {
                    //TODO 登陆失败
                    mvpView.loginFailed(model);
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.showToastShort(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }




    //发送短信
    public void sendMsg(LifecycleTransformer lifecycleTransformer, String phone, TextView textView){
        //TODO 请求获取到短信后
            SendMsgHelper.sendMsg(lifecycleTransformer,textView,phone,"phoneLogin");
    }

    //微信登录
    public void loginWx(){
        loginWX();


    }

    /**
     * 微信登陆
     */
    public void loginWX(){
        if (LaiKaApplication.iwxapi != null && (LaiKaApplication.iwxapi.isWXAppInstalled())){
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            Constants.WXSTATE= UUID.randomUUID().toString();
            req.state =  Constants.WXSTATE;
            LaiKaApplication.iwxapi.sendReq(req);
        } else {
            ToastUtil.showToastLong("您尚未安装微信");
        }
    }

    /**
     * QQ登录
     */
    public void loginQQ(BaseUiListener baseUiListener)
    {
        scope = "all";
        if (!LaiKaApplication.mTencent.isSessionValid())
        {
            LaiKaApplication.mTencent.login(mActivity, scope, baseUiListener);
        }else {
            LaiKaApplication.mTencent.logout(mActivity);
        }
    }
    public void loginRequestQQ(LifecycleTransformer lifecycleTransformer,String openId,String accessToken){
            addSubscription(lifecycleTransformer,apiStores.loginWxQQ("qq", null, openId, accessToken), new ApiCallback<LoginReponse>() {
                @Override
                public void onSuccess(LoginReponse model) {
                    if (model.isSuccess()){
                        MobclickAgent.onProfileSignIn("qq",model.getPayload().getUserId());
                        if(!StringUtil.validText(model.getPayload().getBindPhone())) {
                            SPUtil.putString(Constants.TOKEN, model.getPayload().getToken());
                            IntentLauncher.with(mActivity).launch(BoundPhoneActivity.class);
                        }
                        else {
                            SPUtil.saveObject(Constants.LOGININFO_KEY, model);
                            SPUtil.putString(Constants.TOKEN, model.getPayload().getToken());
                            IntentLauncher.with(mActivity).launch(MainActivity.class);
                        }
//                        IntentLauncher.with(mActivity).launchFinishCpresent(MainActivity.class);
                    }

            }

                @Override
                public void onFailure(String msg) {
                    ToastUtil.showToastShort(msg);
                }

                @Override
                public void onFinish() {

                }
            });
    }

}
