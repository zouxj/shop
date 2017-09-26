package com.shenyu.laikaword.module.mine.systemsetting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.leo618.mpermission.MPermission;
import com.leo618.mpermission.MPermissionSettingsDialog;
import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.module.mine.MineModule;
import com.shenyu.laikaword.module.mine.systemsetting.UserInfoPresenter;
import com.shenyu.laikaword.module.mine.systemsetting.UserInfoView;
import com.shenyu.laikaword.widget.CircleImageView;
import com.zxj.utilslibrary.utils.ImageUtil;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/*
个人信息
 */
public class UserInfoActivity extends LKWordBaseActivity  implements UserInfoView,MPermission.PermissionCallbacks {


    @BindView(R.id.set_change_user_head)
    CircleImageView setChangeUserHead;
    @BindView(R.id.change_tv_name)
    TextView changeTvName;
//    //当前路径
    private String mCurrentPhotoPath;
    @Inject
    UserInfoPresenter userInfoPresenter;
    @Override
    public int bindLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this,this)).inject(this);
    }

    @OnClick({R.id.set_change_user_head, R.id.change_tv_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_change_user_head:
                //TODO 更换头像
                userInfoPresenter.updateImg();
                break;
            case R.id.change_tv_name:
                //TODO 更换名字
                IntentLauncher.with(this).launch(UpdateUserNameActivity.class);
                break;
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
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
                userInfoPresenter.upateInfo(filePath);
            }
        }
        if (requestCode == MPermissionSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
           ToastUtil.showToastShort(R.string.returned_from_app_settings_to_activity+"");
        }
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
            case Constants.RC_CAMERA_PERM:
                userInfoPresenter.cameraTask();
                break;
            //相获取权限返回结果
            case Constants.RC_PHOTO_PERM:
                userInfoPresenter.photoTask();
                break;
        }
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
    public void updateImg(String uri) {
        setChangeUserHead.setImageBitmap(ImageUtil.getBitmap(uri));
    }

    @Override
    public void setImg(String url) {
        mCurrentPhotoPath = url;
    }

}
