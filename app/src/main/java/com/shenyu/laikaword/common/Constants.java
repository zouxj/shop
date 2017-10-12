package com.shenyu.laikaword.common;

import android.net.Uri;

import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.zxj.utilslibrary.utils.SPUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 * 静态常量
 */

public class Constants {
    //SP
    public static final String TOKEN="LOGIN_TOKEN";
    //获取摄像头权限Code
    public static final int RC_CAMERA_PERM = 123;
    //获取相册全向Code
    public static  final int RC_PHOTO_PERM=125;
    public static final int READ_PHONE_STATE=126;
    //相册调取
    public static final int REQUEST_IMAGE_GET = 0;
    //相机调取
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    public static final int FRAGEMTN_MAIN_FLOG = 0x001;
    public static final int FRAGEMTN_CARPACK_FLOG = 0x002;
    public static final String QQ_APPID="101425071";
    public static final String WX_APPID="wx72f8cc7bb98bfa4d";
    public static final String KEY_VALUE="124uj13nejk31h4u3faenfiu3h923jalkd";
    public static synchronized String getToken() {
        return SPUtil.getString(TOKEN,"");
    }
    public static String WXSTATE="";

    //SharedPreferences保存信息KEY
    public static final String LOGININFO_KEY ="USERINFO_LOGIN";
    public static final String MAIN_SHOP_KEY ="MAIN_SHOP_KEY";
    public static synchronized LoginReponse getLoginReponse(){
     LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
        return loginReponse;
    }
}
