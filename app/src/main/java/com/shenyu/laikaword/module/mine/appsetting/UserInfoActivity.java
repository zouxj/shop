package com.shenyu.laikaword.module.mine.appsetting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo618.mpermission.MPermission;
import com.leo618.mpermission.MPermissionSettingsDialog;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.module.MineModule;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.module.mine.appsetting.updateus.UpdateUserNameActivity;
import com.squareup.picasso.Picasso;
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
public class UserInfoActivity extends LKWordBaseActivity  implements UserInfoView,MPermission.PermissionCallbacks {


    @BindView(R.id.set_change_user_head)
    ImageView setChangeUserHead;
    @BindView(R.id.change_tv_name)
    TextView changeTvName;
//    //当前路径
    @BindView(R.id.change_tv_phone)
    TextView tvPhone;
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
        subscribeEvent();
        userInfoPresenter.initUserData();
    }
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_UPDATA_USER:
                                userInfoPresenter.initUserData();
                                break;
                        }
                        LogUtil.e(TAG, myEvent.event+"____"+"threadType=>"+Thread.currentThread());
//            }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e(TAG, "onError");
                        /**
                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
                         */
                        subscribeEvent();
                    }
                });
        RxSubscriptions.add(mRxSub);
    }
    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this,this)).inject(this);
    }

    @OnClick({R.id.set_change_user_head, R.id.set_rl_user_acount_bd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_change_user_head:
                //TODO 更换头像
                userInfoPresenter.updateImg();
                break;
            case R.id.set_rl_user_acount_bd:
                //TODO 更换名字
                if (loginReponse!=null&&loginReponse.getPayload()!=null)
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
                userInfoPresenter.upladHeadImg(filePath);
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
            case Constants.READ_EXTERNAL_STORAGE:
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
    public void loadFailure() {

    }


    @Override
    public void setImg(String url) {
        mCurrentPhotoPath = url;
    }

    @SuppressLint("NewApi")
    @Override
    public void setUserInfo(LoginReponse loginReponse) {
        this.loginReponse= loginReponse;
        if (loginReponse!=null&&null!=loginReponse.getPayload()) {
            changeTvName.setText(loginReponse.getPayload().getNickname());
            tvPhone.setText(loginReponse.getPayload().getBindPhone());
            if (StringUtil.validText(loginReponse.getPayload().getAvatar())) {
                Picasso.with(UIUtil.getContext()).load(loginReponse.getPayload().getAvatar()).placeholder(R.mipmap.left_user_icon)
                        .error(R.mipmap.left_user_icon).transform(new CircleTransform()).into(setChangeUserHead);
            }
        }else{
            changeTvName.setText("");
            setChangeUserHead.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
        }
    }

    @Override
    public void upadteHeadImgStart() {
    }

    @Override
    public void upadteHeadFinsh(boolean bool) {
        if (bool)
        ToastUtil.showToastShort("上传成功");
        else
            ToastUtil.showToastShort("上传失败");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfoPresenter.detachView();
    }

}
