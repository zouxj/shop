package com.shenyu.laikaword.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.githang.statusbar.StatusBarCompat;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.helper.LoadViewHelper;
import com.shenyu.laikaword.Interactor.IBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.us.bankcard.ui.activity.AddBankCardActivity;
import com.shenyu.laikaword.module.us.setpassword.SetPassWordMsgCodeActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zxj.utilslibrary.utils.ActivityManageUtil;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public abstract class LKWordBaseActivity extends RxAppCompatActivity implements IBaseActivity{
    protected final String TAG = getClass().getSimpleName();
    protected Activity mActivity;
    protected  TextView mToolbarTitle;
    protected  TextView mToolbarSubTitle;
    protected  Toolbar mToolbar;
    protected RelativeLayout leftTitle;
    protected  TextView mLeftTitile;
    protected Subscription mRxSub;
    protected LoadViewHelper loadViewHelper;
    protected RetrofitUtils retrofitUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView( bindLayout());
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, UIUtil.getColor(R.color.app_theme_red));
        mActivity = this;
        setupActivityComponent();
        ActivityManageUtil.getAppManager().addActivity(this);
        initToolBar();
        initView();
        loadViewHelper = LoadViewHelper.instanceLoadViewHelper();
        retrofitUtils =RetrofitUtils.getRetrofitUtils().setLifecycleTransformer(this.bindToLifecycle());
        doBusiness(this);
        LogUtil.i("shenyu", "onCreate???" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        dumpTaskAffinity();
        
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mRxSub);
        ActivityManageUtil.getAppManager().finishActivity(this);
    }

  @SuppressLint("WrongViewCast")
  public void  initToolBar(){
      leftTitle = findViewById(R.id.rl_toolbar_left);
      mToolbar = (Toolbar) findViewById(R.id.toolbar);
      mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
      mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
      mLeftTitile = findViewById(R.id.toolbar_left);


      if (mToolbar != null) {
          //???Toolbar???????????????
          setSupportActionBar(mToolbar);
      }
      if (mLeftTitile!=null){
          mLeftTitile.setText("??????");

      }
      if (mToolbarTitle != null) {
          //getTitle()?????????activity???android:lable?????????
          mToolbarTitle.setText(getTitle());
          //??????????????????????????????
          getSupportActionBar().setDisplayShowTitleEnabled(false);
      }
  }

    /**
     * ??????Title
     */
  @SuppressLint("NewApi")
  public void setToolBarRight(String rightTitle, int bgDrawable){
      if (null!=rightTitle)
      mToolbarSubTitle.setText(rightTitle);
      if (bgDrawable!=0)
          if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
              mToolbarSubTitle.setBackground(UIUtil.getDrawable(bgDrawable));
          else
              mToolbarSubTitle.setBackgroundResource(bgDrawable);

  }
    /**
     * ??????????????????
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if(mToolbarTitle != null){
            mToolbarTitle.setText(title);
        }else{
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }
    /**
     * this Activity of tool bar.
     * ????????????.
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }
    public void setToolBarLeft(int drawableID,String ex,int gone){
        if (gone==View.GONE) {
            leftTitle.setVisibility(gone);
            return;
        }
        if (null!=mLeftTitile){
            mLeftTitile.setText(ex);
            Drawable bgDrawable= UIUtil.getDrawable(drawableID);
///???????????????????????,??????????????????.????
            bgDrawable.setBounds(0, 0, bgDrawable.getMinimumWidth(),bgDrawable.getMinimumHeight());
            mLeftTitile.setCompoundDrawables(bgDrawable,null,null,null);
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                mLeftTitile.setBackground(UIUtil.getDrawable(drawableID));
            else
                mLeftTitile.setBackgroundResource(drawableID);


        }
}

    /**
     * ???????????????21?????????????????????
     */

    private void showBack(){
        //setNavigationIcon?????????setSupportActionBar(toolbar);???????????????
        if(null!=mLeftTitile) {
            findViewById(R.id.rl_toolbar_left).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

    }
    /**
     * ????????????????????????,????????????,???????????????????????????.
     * @return
     */
    protected boolean isShowBacking(){
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        /**
         * ???????????????Toolbar,???????????????????????????
         */
        if(null != getToolbar() && isShowBacking()){
            showBack();
        }
    }


    /**
     * ????????????Test
     *          payApi = new PayApi();
     payApi.bargainorId=this.bargainorId;
     payApi.appId = this.appId;
     payApi.serialNumber = this.
     payApi.callbackScheme = callbackScheme;
     payApi.tokenId = this.tokenId;
     payApi.pubAcc = "";
     payApi.pubAccHint = "";
     payApi.nonce = String.valueOf(System.currentTimeMillis());
     payApi.timeStamp = System.currentTimeMillis() / 1000;
     payApi.bargainorId = this.bargainorId;
     */




    /**
     * ?????????View
     */
    public void initView(){}

    /**
     * ???????????????
     */
    public void heideSoftInput(){
        if (KeyBoardUtil.isActive(this)) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                        0);
            }
        }
    }

    protected void dumpTaskAffinity(){
        try {
            ActivityInfo info = this.getPackageManager()
                    .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            LogUtil.i("shenyu", "taskAffinity:"+info.taskAffinity);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.i("shenyu", "*****onNewIntent()??????*****");
        LogUtil.i("shenyu", "onNewIntent???" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        dumpTaskAffinity();
    }

    /**
     * ??????????????????
     */
    protected void  refreshUser(){
        retrofitUtils.setLifecycleTransformer(bindUntilEvent(ActivityEvent.DESTROY)).addSubscription(RetrofitUtils.apiStores.getUserInfo(), new ApiCallback<LoginReponse>() {
            @Override
            public void onSuccess(LoginReponse loginReponse) {
                if (loginReponse.isSuccess()){
                    SPUtil.saveObject(Constants.LOGININFO_KEY,loginReponse);
                    RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER,null));
                }

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
    protected void toAddBank() {
        LoginReponse loginReponse= Constants.getLoginReponse();
        if (loginReponse.getPayload().getIsSetTransactionPIN()==0){
            DialogHelper.commonDialog(mActivity, "????????????", "???????????????????????????", "??????", "?????????", false, new DialogHelper.ButtonCallback() {
                @Override
                public void onNegative(Dialog dialog) {
                    IntentLauncher.with(mActivity).launch(SetPassWordMsgCodeActivity.class);
                }

                @Override
                public void onPositive(Dialog dialog) {

                }
            }).show();
            return;
        }
        DialogHelper.setInputDialog(mActivity, true,null,"", new DialogHelper.LinstenrText() {
            @Override
            public void onLintenerText(String passWord) {
                retrofitUtils.setLifecycleTransformer(bindToLifecycle()).addSubscription(RetrofitUtils.apiStores.validateTransactionPIN(passWord), new ApiCallback<BaseReponse>() {
                    @Override
                    public void onSuccess(BaseReponse model) {
                        if (model.isSuccess())
                            IntentLauncher.with(mActivity).launch(AddBankCardActivity.class);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.showToastShort(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
            }

            @Override
            public void onWjPassword() {

            }
        }).show();

    }
}
