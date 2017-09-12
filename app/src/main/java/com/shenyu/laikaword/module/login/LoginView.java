package com.shenyu.laikaword.module.login;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.bean.reponse.UserReponse;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public interface LoginView  extends BaseLoadView {
    void canLogin(boolean canLogin);
    void showUser(UserReponse user);
}
