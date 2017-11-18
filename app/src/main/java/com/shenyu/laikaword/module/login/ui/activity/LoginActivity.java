package com.shenyu.laikaword.module.login.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.leo618.mpermission.MPermission;
import com.leo618.mpermission.MPermissionSettingsDialog;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.Interactor.BaseUiListener;
import com.shenyu.laikaword.di.module.LoginModule;
import com.shenyu.laikaword.module.login.presenter.LoginPresenter;
import com.shenyu.laikaword.module.login.view.LoginView;
import com.tencent.connect.common.Constants;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 用户登录页面
 */
public class LoginActivity extends LKWordBaseActivity implements LoginView,MPermission.PermissionCallbacks {

    @BindView(R.id.et_use_phone)
    EditText etUsePhone;
    @BindView(R.id.et_user_msg_code)
    EditText etUserMsgCode;
    @BindView(R.id.tv_send_msg_code)
    TextView tvSendMsgCode;
    @BindView(R.id.bt_login)
    TextView btLogin;
    @Inject
    LoginPresenter loginPresenter;
    BaseUiListener iLoginListener;
    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void doBusiness(Context context) {



        RxTextView.textChanges(etUsePhone).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                if (StringUtil.validText(etUsePhone.getText().toString().trim())&&etUsePhone.getText().toString().length()>=11){
                    tvSendMsgCode.setTextColor(UIUtil.getColor(R.color.app_theme_red));
                    tvSendMsgCode.setEnabled(true);
                }
                else {
                    tvSendMsgCode.setTextColor(UIUtil.getColor(R.color.color_b0b0));
                    tvSendMsgCode.setEnabled(false);
                }
                loginPresenter.checkInput(charSequence.toString(), etUserMsgCode.getText().toString());
            }
        });
        RxTextView.textChanges(etUserMsgCode).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                loginPresenter.checkInput(etUsePhone.getText().toString(), charSequence.toString());
            }
        });
        iLoginListener = new BaseUiListener(){
            @Override
            public void onComplete(Object response) {
                JSONObject responseJsonobject = (JSONObject) response;
                final String openid = responseJsonobject.optString("openid");
                final String access_token = responseJsonobject.optString("access_token");
                final String expires_in = responseJsonobject.optString("expires_in");
                loginPresenter.loginRequestQQ(LoginActivity.this.bindToLifecycle(),openid,access_token);
            }
        };
    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new LoginModule(this,this)).inject(this);
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void dataCountChanged(int count) {

    }

    @Override
    public void loadFinished() {

    }

    @Override
    public void loadFailure() {

    }

    @SuppressLint("NewApi")
    @Override
    public void canLogin(boolean canLogin) {
        if (canLogin) {
            btLogin.setEnabled(true);
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                btLogin.setBackground(UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle_light));
            else
                btLogin.setBackgroundResource(R.drawable.bg_bt_login_rectangle_light);
        } else {
            btLogin.setEnabled(false);
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                btLogin.setBackground(UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle));
            else
                btLogin.setBackgroundResource(R.drawable.bg_bt_login_rectangle);
        }
    }

    @Override
    public void showUser(LoginReponse user) {
    }


    @OnClick({R.id.bt_login, R.id.tv_send_msg_code,R.id.tv_qq_login,R.id.tv_wechat_login,R.id.iv_login_close})
    public void onViewClicked(View view) {
        String code = etUserMsgCode.getText().toString().trim();
        String phone = etUsePhone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.bt_login:
                loginPresenter.login(this.bindToLifecycle(),phone, code);
                break;
            case R.id.tv_send_msg_code:
                loginPresenter.sendMsg(bindToLifecycle(),phone, tvSendMsgCode);
                break;
            case R.id.tv_qq_login:
                loginPresenter.loginQQ(iLoginListener);
                break;
            case R.id.tv_wechat_login:
                if (MPermission.hasPermissions(mActivity, Manifest.permission.READ_PHONE_STATE)) {
                    // Have permission, do the thing!
                    loginPresenter.loginWx();

                } else {
                    // Ask for one permission
                    MPermission.requestPermissions(mActivity,"微信登录需要获取"+ UIUtil.getString(R.string.read_phone_state), com.shenyu.laikaword.common.Constants.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
                }

                break;
            case R.id.iv_login_close:
                //TODO 退出登录
                finish();
                break;
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.d("TAG", "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            LaiKaApplication.mTencent.onActivityResultData(requestCode,resultCode,data,iLoginListener);
        }
        if (requestCode == MPermissionSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            ToastUtil.showToastShort(R.string.write_external_storage +"");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        LogUtil.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (MPermission.somePermissionPermanentlyDenied(this, perms)) {
            new MPermissionSettingsDialog.Builder(this).build().show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //相机获取权限返回结果
            case com.shenyu.laikaword.common.Constants.READ_PHONE_STATE:
//                ToastUtil.showToastShort(R.string.write_external_storage +"");
                loginPresenter.loginWx();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }
}
