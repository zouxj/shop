package com.shenyu.laikaword.Interactor;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.zxj.utilslibrary.utils.LogUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/19 0019.
 * QQ登录回调
 */

public class BaseUiListener implements IUiListener {
    @Override
    public void onComplete(Object response) {
//        JSONObject responseJsonobject = (JSONObject) response;
//        final String openid = responseJsonobject.optString("openid");
//        final String access_token = responseJsonobject.optString("access_token");
//        final String expires_in = responseJsonobject.optString("expires_in");
//        LaiKaApplication.mTencent.setOpenId(openid);
//        LaiKaApplication.mTencent.setAccessToken(access_token,expires_in);
//        QQToken qqToken = LaiKaApplication.mTencent.getQQToken();
//        UserInfo info = new UserInfo(UIUtil.getContext(), qqToken);
//        info.getUserInfo(new IUiListener() {
//            @Override
//            public void onComplete(Object response) {
//                JSONObject jsonObject = (JSONObject) response;
//                String  nickname = jsonObject.optString("nickname");
//                /** QQ登录成功后，获取相关信息，登录应用*/
//                //TODO 获取信息成功登录应用
////                        loginModel.loginFromWeiboAndQQ("qq", openid, access_token,
////                                expires_in, nickname, BeeFrameworkApp.getInstance().getImei(),
////                                SharedPrefsUtil.getString(E_SigninActivity.this,
////                                        AppConst.LATITUDE), SharedPrefsUtil.getString(
////                                        E_SigninActivity.this, AppConst.LONGITUDE));
//            }
//
//            @Override
//            public void onError(UiError uiError) {
//
//            }
//            @Override
//            public void onCancel() {
//
//            }
//        });
    }

    @Override
    public void onError(UiError uiError) {
        LogUtil.d(" 取消"+uiError.errorDetail);
    }

    @Override
    public void onCancel() {
LogUtil.d(" 取消");
    }

}
