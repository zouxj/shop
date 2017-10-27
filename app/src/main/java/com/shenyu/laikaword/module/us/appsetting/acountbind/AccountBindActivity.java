package com.shenyu.laikaword.module.us.appsetting.acountbind;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leo618.mpermission.MPermission;
import com.leo618.mpermission.MPermissionSettingsDialog;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.Interactor.BaseUiListener;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountBindActivity extends LKWordBaseActivity {


    @BindView(R.id.tv_bd_phone)
    TextView tvBdPhone;
    @BindView(R.id.tv_bd_wechat)
    TextView tvBdWechat;
    @BindView(R.id.tv_bd_qq)
    TextView tvBdQq;
    @BindView(R.id.rl_bd_phone)
    RelativeLayout rlBdPhone;
    @BindView(R.id.rl_bd_wechat)
    RelativeLayout rlBdWechat;
    @BindView(R.id.rl_bd_qq)
    RelativeLayout rlBdQq;
    private BaseUiListener iLoginListener;
    private String scope; //获取信息的范围参数

    @Override
    public int bindLayout() {
        return R.layout.activity_account_bind;
    }

    @Override
    public void initView() {
        setToolBarTitle("账号绑定");
    }

    @Override
    public void doBusiness(Context context) {
        iLoginListener = new BaseUiListener() {
            @Override
            public void onComplete(Object response) {
                JSONObject responseJsonobject = (JSONObject) response;
                final String openid = responseJsonobject.optString("openid");
                final String access_token = responseJsonobject.optString("access_token");
                final String expires_in = responseJsonobject.optString("expires_in");
                acountbdQQ(openid, access_token);
            }
        };
        LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
        if (null != loginReponse) {
            if (StringUtil.validText(loginReponse.getPayload().getBindPhone())) {
                tvBdPhone.setText(loginReponse.getPayload().getBindPhone());
                rlBdPhone.setEnabled(false);
            }
            if (StringUtil.validText(loginReponse.getPayload().getBindWeChat())) {
                tvBdWechat.setText(loginReponse.getPayload().getBindWeChat());
                rlBdWechat.setEnabled(false);
            }
            if (StringUtil.validText(loginReponse.getPayload().getBindQQ())) {
                tvBdQq.setText(loginReponse.getPayload().getBindQQ());
                rlBdQq.setEnabled(false);
            }

        }
    }

    @Override
    public void setupActivityComponent() {

    }


    @OnClick({R.id.rl_bd_phone, R.id.rl_bd_wechat, R.id.rl_bd_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bd_phone:
                //TODO 手机号绑定
                IntentLauncher.with(this).launch(BoundPhoneActivity.class);
                break;
            case R.id.rl_bd_wechat:
                //TODO 微信绑定
                mpBdWx();
                break;
            case R.id.rl_bd_qq:
                //TODO QQ绑定
                acountbdQQ(iLoginListener);
                break;
        }
    }

    //微信登录
    public void mpBdWx() {
        if (MPermission.hasPermissions(mActivity, Manifest.permission.READ_PHONE_STATE)) {
            // Have permission, do the thing!
            acountbdWx();
        } else {
            // Ask for one permission
            MPermission.requestPermissions(mActivity, UIUtil.getString(R.string.read_phone_state), Constants.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
        }

    }

    /**
     * 微信绑定
     */
    public void acountbdWx() {
        if (LaiKaApplication.iwxapi != null && (LaiKaApplication.iwxapi.isWXAppInstalled())) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "ACOUNT_BD";
            LaiKaApplication.iwxapi.sendReq(req);
        } else {
            ToastUtil.showToastLong("您尚未安装微信");
        }
    }

    /**
     * QQ绑定
     */
    public void acountbdQQ(BaseUiListener baseUiListener) {
        scope = "all";
        if (!LaiKaApplication.mTencent.isSessionValid()) {
            LaiKaApplication.mTencent.login(mActivity, scope, baseUiListener);
        } else {
            LaiKaApplication.mTencent.logout(mActivity);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.d("TAG", "-->onActivityResult " + requestCode + " resultCode=" + resultCode);
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN ||
                requestCode == com.tencent.connect.common.Constants.REQUEST_APPBAR) {
            LaiKaApplication.mTencent.onActivityResultData(requestCode, resultCode, data, iLoginListener);
        }
        if (requestCode == MPermissionSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            ToastUtil.showToastShort(R.string.returned_from_app_settings_to_activity + "");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void acountbdQQ(String openId, String accessToken) {

        HashMap<String, String> param = new HashMap<>();
        param.put("loginType", "QQ");
        param.put("openId", openId);
        param.put("accessToken", accessToken);
        retrofitUtils.addSubscription(RetrofitUtils.apiStores.partyBind(param), new ApiCallback<LoginReponse>() {
            @Override
            public void onSuccess(LoginReponse model) {
                if (model.isSuccess()) {
                    SPUtil.saveObject(Constants.LOGININFO_KEY, model);
                    SPUtil.putString(Constants.TOKEN, model.getPayload().getToken());
                    IntentLauncher.with(AccountBindActivity.this).launch(AcountBdingSuccessActivity.class);
                } else {
                    ToastUtil.showToastShort(model.getError().getMessage());

                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.showToastShort(msg);

            }

            @Override
            public void onFinish() {
                finish();
            }
        });

    }
}
