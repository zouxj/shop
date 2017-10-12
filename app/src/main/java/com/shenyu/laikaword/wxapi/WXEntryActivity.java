package com.shenyu.laikaword.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.main.activity.MainActivity;
import com.shenyu.laikaword.module.login.LoginPresenter;
import com.shenyu.laikaword.module.mine.systemsetting.activity.AcountBdingSuccessActivity;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import rx.internal.util.ObserverSubscriber;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    //第三方app和微信通信的openapid接口
    // 这两个参数在文档中没有找到，可能是瞎了,,,自己在代码里面找了会才找到，这两个常量代表了微信返回的消息类型，是对登录的处理还是对分享的处理，登录会在后面介绍到
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LaiKaApplication.iwxapi = WXAPIFactory.createWXAPI(this, Constants.WX_APPID);
        LaiKaApplication.iwxapi.registerApp(Constants.WX_APPID);
        LaiKaApplication.iwxapi.handleIntent(this.getIntent(), this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LaiKaApplication.iwxapi.handleIntent(intent, this);//必须调用此句话
    }
    /**
     * 微信发送的请求将回调此方法
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.d("req", baseReq.toString());
    }
    /**
     * 发送到微信请求的相应结果
     *
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            //发送成功
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp response = (SendAuth.Resp) resp;
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        LogUtil.i("code = " + code);
                        // 判断请求是否是我的应用的请求
                        if (response.state == null || !response.state.equals(Constants.WXSTATE)){
                                        return;
                        }
                        if (response.state.equals("ACOUNT_BD")){
                            //TODO 绑定用户信息
                            IntentLauncher.with(this).launch(AcountBdingSuccessActivity.class);
                        }else {
                            RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.loginWxQQ("WeChat", code, "", ""), new ApiCallback<LoginReponse>() {
                                @Override
                                public void onSuccess(LoginReponse model) {
                                    if (model.isSuccess()) {
                                        SPUtil.saveObject(Constants.LOGININFO_KEY, model);
                                        SPUtil.putString(Constants.TOKEN, model.getPayload().getToken());
                                        IntentLauncher.with(WXEntryActivity.this).launch(MainActivity.class);
                                    } else {
                                        ToastUtil.showToastShort(model.getError().getMessage());
                                    }
                                }

                                @Override
                                public void onFailure(String msg) {
                                    finish();

                                }

                                @Override
                                public void onFinish() {
                                    finish();

                                }

                            });
                        }
                        //TODO 发送网络请求
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtil.showToastShort("微信分享成功");
                        finish();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtil.showToastShort("登录取消");
                finish();
                //TODO 分享失败
                break;
            //发送拒绝
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtil.showToastShort("登录失败");
                finish();
                //TODO 登陆失败
                break;

        }
    }
}
