package com.shenyu.laikaword.module.login;

import android.widget.TextView;

import com.shenyu.laikaword.bean.reponse.UserReponse;
import com.shenyu.laikaword.helper.SendMsgHelper;
import com.shenyu.laikaword.http.NetWorks;
import com.shenyu.laikaword.http.uitls.SimpleCallback;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import dagger.Module;
import rx.Observer;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class LoginPresenter {

    private final LoginView loginView;
    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }


    public void checkInput(String username,String password){
        loginView.canLogin(StringUtil.validText(username)&&StringUtil.validText(password));
    }

    public void login(String username,String password){
        NetWorks.loginUser(username, password, new SimpleCallback<UserReponse>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(UserReponse userReponse) {
                loginView.showUser(userReponse);
            }

            @Override
            public void onComplete() {

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
//        mAddBankView.setMsgCode(phone);
    }
    //QQ登录
    public void loginQQ(){

    }
    //微信登录
    public void loginWx(){}{

    }
}
