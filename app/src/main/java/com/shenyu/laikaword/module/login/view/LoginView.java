package com.shenyu.laikaword.module.login.view;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public interface LoginView  extends BaseLoadView {
    void showUser(LoginReponse user);
}
