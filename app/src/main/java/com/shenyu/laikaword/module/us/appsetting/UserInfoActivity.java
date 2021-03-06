package com.shenyu.laikaword.module.us.appsetting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.leo618.mpermission.MPermission;
import com.leo618.mpermission.MPermissionSettingsDialog;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.module.mine.MineModule;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.address.ui.activity.AddressInfoActivity;
import com.shenyu.laikaword.module.us.appsetting.updateus.UpdateUserNameActivity;
import com.shenyu.laikaword.module.us.bankcard.ui.activity.BankInfoActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/*
个人信息
 */
public class UserInfoActivity extends LKWordBaseActivity implements UserInfoView, MPermission.PermissionCallbacks {


    @BindView(R.id.set_change_user_head)
    ImageView setChangeUserHead;
    @BindView(R.id.change_tv_name)
    TextView changeTvName;
    //    //当前路径
    @BindView(R.id.tv_bank_count)
    TextView tvBankCount;
    @BindView(R.id.tv_nickname)
    TextView tVUserName;
    private String mCurrentPhotoPath;
    @Inject
    UserInfoPresenter userInfoPresenter;
    LoginReponse loginReponse;

    @Override
    public int bindLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        super.initView();
        setToolBarTitle("个人资料");

    }

    @Override
    public void doBusiness(Context context) {
        //修改时重新刷新数据
        userInfoPresenter.initUserData();
    }


    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this, this)).inject(this);
    }

    @OnClick({R.id.set_change_user_head, R.id.set_rl_user_acount_bd,R.id.rl_bank,R.id.rl_shouhuo_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_change_user_head:
                //TODO 更换头像
                if (MPermission.hasPermissions(this, Manifest.permission.CAMERA) && MPermission.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    userInfoPresenter.updateImg(bindUntilEvent(ActivityEvent.DESTROY));
                } else {
                    // Ask for one permission
                    MPermission.requestPermissions(this, "使用摄像头需要"
                                    + UIUtil.getString(R.string.read_camere), Constants.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                break;
            case R.id.set_rl_user_acount_bd:
                //TODO 更换名字
                if (loginReponse != null && loginReponse.getPayload() != null)
                    IntentLauncher.with(this).launch(UpdateUserNameActivity.class);
                break;
            case R.id.rl_bank:
                //TODO 银行卡
                IntentLauncher.with(this).launch(BankInfoActivity.class);
                break;
            case R.id.rl_shouhuo_address:
                //TODO 收货地址
                IntentLauncher.with(this).launch(AddressInfoActivity.class);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String filePath = null;
            //判断是哪一个的回调
            if (requestCode == Constants.REQUEST_IMAGE_GET) {
                //返回的是content://的样式
                filePath = userInfoPresenter.getFilePathFromContentUri(data.getData());
            } else if (requestCode == Constants.REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    filePath = mCurrentPhotoPath;
                }
            }
            if (!TextUtils.isEmpty(filePath)) {
                userInfoPresenter.upladHeadImg(filePath, bindUntilEvent(ActivityEvent.DESTROY));
            }
        }
        if (requestCode == MPermissionSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
//           ToastUtil.showToastShort(UIUtil.getString(R.string.write_external_storage));
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (MPermission.somePermissionPermanentlyDenied(this, perms)) {
            new MPermissionSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //相机获取权限返回结果
            case Constants.READ_EXTERNAL_STORAGE:
//                ToastUtil.showToastShort(UIUtil.getString(R.string.write_external_storage));
//                userInfoPresenter.updateImg(bindUntilEvent(ActivityEvent.DESTROY));
//                LogUtil.i("READ_EXTERNAL_STORAGE","cameraTask");
                break;
            //相获取权限返回结果
//            case Constants.READ_EXTERNAL_STORAGE:
//                userInfoPresenter.updateImg(bindUntilEvent(ActivityEvent.DESTROY));
//                LogUtil.i("RC_PHOTO_PERM","photoTask");
//                break;
        }
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void loadSucceed(BaseReponse baseReponse) {

    }


    @Override
    public void loadFailure() {

    }


    @Override
    public void setImg(String url) {
        mCurrentPhotoPath = url;
    }

    @SuppressLint("NewApi")
    @Override
    public void setUserInfo(LoginReponse loginReponse) {
        this.loginReponse = loginReponse;
        if (loginReponse != null && null != loginReponse.getPayload()) {
            changeTvName.setText(loginReponse.getPayload().getNickname());
            tVUserName.setText(loginReponse.getPayload().getUserName());
            if (StringUtil.validText(loginReponse.getPayload().getBankCardNum()))
            tvBankCount.setText(loginReponse.getPayload().getBankCardNum().equals("0")?"":loginReponse.getPayload().getBankCardNum());
            ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(), setChangeUserHead);
        } else {
            changeTvName.setText("");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                setChangeUserHead.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
            else
                setChangeUserHead.setBackgroundResource(R.mipmap.left_user_icon);
        }
    }

    @Override
    public void upadteHeadImgStart() {
    }

    @Override
    public void upadteHeadFinsh(boolean bool) {
        if (bool) {
            ToastUtil.showToastShort("上传成功");
            RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
        } else
            ToastUtil.showToastShort("上传失败");
    }

    @Override
    public void subscribeEvent(Event event) {
        switch (event.event) {
            case EventType.ACTION_UPDATA_USER:
                userInfoPresenter.initUserData();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfoPresenter.detachView();
    }


}
