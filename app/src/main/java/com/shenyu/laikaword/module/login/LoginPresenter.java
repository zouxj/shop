package com.shenyu.laikaword.module.login;

import com.shenyu.laikaword.bean.reponse.UserReponse;
import com.shenyu.laikaword.http.NetWorks;
import com.shenyu.laikaword.http.uitls.SimpleCallback;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;

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

}
