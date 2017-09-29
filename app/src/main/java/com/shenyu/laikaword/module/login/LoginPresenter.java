package com.shenyu.laikaword.module.login;

import android.Manifest;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.leo618.mpermission.MPermission;
import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.bean.BaseResponse;
import com.shenyu.laikaword.bean.ReRequest;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.bean.reponse.UserReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.SendMsgHelper;
import com.shenyu.laikaword.http.NetWorks;
import com.shenyu.laikaword.http.uitls.SimpleCallback;
import com.shenyu.laikaword.interfaces.BaseUiListener;
import com.shenyu.laikaword.main.activity.MainActivity;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.tencent.connect.UserInfo;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
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
    }


    public void checkInput(String username,String password){
        mvpView.canLogin(StringUtil.validText(username)&&StringUtil.validText(password));
    }

    public void login(String phone,String code){
        addSubscription(apiStores.loginPhone(phone,code), new ApiCallback<LoginReponse>() {
            @Override
            public void onSuccess(LoginReponse model) {
                if (model.isSuccess()){
                    //TODO 登录成功
                    SPUtil.putString(Constants.TOKEN,model.getPayload().getToken());
                    SPUtil.saveObject(Constants.LOGININFO_KEY,model);
                    ToastUtil.showToastShort("登录成功");
                    IntentLauncher.with(mActivity).launch(MainActivity.class);
                    mActivity.finish();;

                }else{
                    //TODO 登陆失败
                    ToastUtil.showToastShort(model.getError().getMessage());
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

    /**
     * 保存用户名
     * @param username
     * @param password
     */
    public void saveLoginInfo(String username,String password){
        SPUtil.putString("usename",username);
        SPUtil.putString("password",password);
    }

    public String getUserNameFromLocal(){
        return SPUtil.getString("usename","admin");
    }


    public String getPasswordFromLocal(){
        return SPUtil.getString("password","123456");
    }

    //发送短信
    public void sendMsg(String phone, TextView textView){
        //TODO 请求获取到短信后
        if (StringUtil.isTelNumber(phone))
            SendMsgHelper.sendMsg(textView,phone);
        else
            ToastUtil.showToastShort("请输入有效手机号码");
    }

    //微信登录
    public void loginWx(){
        if (MPermission.hasPermissions(mActivity, Manifest.permission.READ_PHONE_STATE)) {
            // Have permission, do the thing!
            loginWX();
        } else {
            // Ask for one permission
            MPermission.requestPermissions(mActivity, UIUtil.getString(R.string.rationale_camera), Constants.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
        }

    }
    public void loginWX(){
        if (LaiKaApplication.iwxapi != null && (LaiKaApplication.iwxapi.isWXAppInstalled())){
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            Constants.WXSTATE= UUID.randomUUID().toString();
            req.state =  Constants.WXSTATE;;
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
    public void loginRequestQQ(String openId,String accessToken){
            addSubscription(apiStores.loginWxQQ("qq", null, openId, accessToken), new ApiCallback<LoginReponse>() {
                @Override
                public void onSuccess(LoginReponse model) {
                    if (model.isSuccess()){
                        SPUtil.saveObject(Constants.LOGININFO_KEY,model);
                        SPUtil.putString(Constants.TOKEN,model.getPayload().getToken());
                        IntentLauncher.with(mActivity).launch(MainActivity.class);
                    }else {
                        ToastUtil.showToastShort(model.getError().getMessage());
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
