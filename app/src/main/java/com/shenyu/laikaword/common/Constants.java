package com.shenyu.laikaword.common;

import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.zxj.utilslibrary.utils.SPUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 * 静态常量
 */

public class Constants {
    /**
     * URL Host
     */
    public static String HOST="http://t.shop.comingcard.com/";
    static {
        if (false){
            HOST="https://api.buycardlife.com/";
        }
    }
    public final static String HOSTDOWN_URL=HOST;
    public static String API_URL =HOST+ "MApi/";
    public static String webURL = HOST+"view/detail/detail.html?goodsId=";

    /**
     * xml存在key
     */
    public static final String SAVA_ADDRESS = "ADDRESS_INFO";
    public static final String TOKEN="LOGIN_TOKEN";
    //获取摄像头权限Code
    public static final int READ_EXTERNAL_STORAGE = 123;
    //获取相册全向Code
    public static  final int RC_PHOTO_PERM=125;
    public static final int READ_PHONE_STATE=126;
    public static final int INTALL_APK=127;
    //相册调取
    public static final int REQUEST_IMAGE_GET = 0;
    //相机调取
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    //地址调取
    public static final int REQUEST_ADDRESS= 2;
    /**
     * appID
     */
    public static final String QQ_APPID="101425071";
    public static final String WX_APPID="wx72f8cc7bb98bfa4d";
    public static final String KEY_VALUE="124uj13nejk31h4u3faenfiu3h923jalkd";
    public static final String UMENG_ID="59ffd891aed1797aa8000196";
    public static synchronized String getToken() {
        return SPUtil.getString(TOKEN,"");
    }
    public static String WXSTATE="WX_LOGIN";
    //SharedPreferences保存信息KEY
    public static final String LOGININFO_KEY ="USERINFO_LOGIN";
    public static final String MAIN_SHOP_KEY ="MAIN_SHOP_KEY";
    public  static String VERSION_NEW ="";
    public static synchronized LoginReponse getLoginReponse(){
     LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
        return loginReponse;
    }
}
