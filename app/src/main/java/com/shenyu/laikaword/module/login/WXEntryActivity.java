package com.shenyu.laikaword.module.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxj.utilslibrary.utils.LogUtil;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    //微信appid
    public static final String APP_ID = "wx9af20b2f6c6c126f";
    //微信appSecret
    private String APP_SECRET = "8378bf6e79a1ae0d1249c535010f5df1";
    //第三方app和微信通信的openapid接口
    // 这两个参数在文档中没有找到，可能是瞎了,,,自己在代码里面找了会才找到，这两个常量代表了微信返回的消息类型，是对登录的处理还是对分享的处理，登录会在后面介绍到
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private IWXAPI api;
    private JsonObject jsonObject = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.handleIntent(this.getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);//必须调用此句话
    }

    /**
     * 微信发送的请求将回调此方法
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.d("req", baseReq.toString());
        finish();
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
                SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                if (sendResp != null) {
                    String code = sendResp.code;
                    getAccess_token(code);
                }
                if (resp.getType() ==  RETURN_MSG_TYPE_SHARE) {

                }
                finish();
                break;
            //发送取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            //发送拒绝
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            default:
                //发送返回
                break;
        }
    }

    /**
     * 获取openid  accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAccess_token(String code) {

        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + APP_ID
                + "&secret="
                + APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";
        //TODO 获取用户参数
//        Request<String> info = NoHttp.createStringRequest(path, RequestMethod.GET);
//        queue.add(0, info, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                String json=response.get();
//                try {
//                    JSONObject object=new JSONObject(json);
//                    //获取用户的openid
//                    String openid=object.getString("openid");
//                    String access_token=object.getString("access_token");
//                    getUserMsg(access_token,openid);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });
    }

    /**
     * 获取微信个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMsg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;

        //TODO 获取个人信息参数
//        Request<String> info = NoHttp.createStringRequest(path, RequestMethod.GET);
//        queue.add(0, info, new OnResponseListener<String>() {
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<String> response) {
////
//                Log.i("wch",response.get()+"");
//                Gson gson = new Gson();
//                WXLoginBean wxLoginBean = gson.fromJson(response.get(), WXLoginBean.class);
//                String nickname = wxLoginBean.getNickname();
//
//            }
//
//            @Override
//            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//                Toast.makeText(WXEntryActivity.this, "获取失败:" + responseCode, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });

    }

}
