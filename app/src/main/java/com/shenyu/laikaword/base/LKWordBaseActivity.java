package com.shenyu.laikaword.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.githang.statusbar.StatusBarCompat;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.interfaces.IBaseActivity;
import com.shenyu.laikaword.widget.loaddialog.LoadingDialog;
import com.zxj.utilslibrary.utils.ActivityManageUtil;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import butterknife.ButterKnife;
/**
 * Created by Administrator on 2017/8/2 0002.
 */

public abstract class LKWordBaseActivity extends AppCompatActivity implements IBaseActivity{
    protected final String TAG = getClass().getSimpleName();
    protected Activity mActivity;
    protected  TextView mToolbarTitle;
    protected  TextView mToolbarSubTitle;
    protected  Toolbar mToolbar;
    protected  TextView mLeftTitile;
    protected  static  LoadingDialog ld;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( bindLayout());
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, UIUtil.getColor(R.color.app_theme_red));
        mActivity = this;
        setupActivityComponent();
        ActivityManageUtil.getAppManager().addActivity(this);
        initToolBar();
        initView();
        ld =new LoadingDialog(this);
        ld.setLoadingText("加载中")
                .setSuccessText("加载成功")//显示加载成功时的文字
                .setFailedText("加载失败");
        doBusiness(this);
        
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManageUtil.getAppManager().finishActivity(this);
    }

  public void  initToolBar(){
      mToolbar = (Toolbar) findViewById(R.id.toolbar);
      mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
      mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
      mLeftTitile = findViewById(R.id.toolbar_left);


      if (mToolbar != null) {
          //将Toolbar显示到界面
          setSupportActionBar(mToolbar);
      }
      if (mLeftTitile!=null){
          mLeftTitile.setText("返回");

      }
      if (mToolbarTitle != null) {
          //getTitle()的值是activity的android:lable属性值
          mToolbarTitle.setText(getTitle());
          //设置默认的标题不显示
          getSupportActionBar().setDisplayShowTitleEnabled(false);
      }
  }

    /**
     * 右边Title
     */
  @SuppressLint("NewApi")
  public void setToolBarRight(String rightTitle, int bgDrawable){
      if (null!=rightTitle)
      mToolbarSubTitle.setText(rightTitle);
      if (bgDrawable!=0)
      mToolbarSubTitle.setBackground(UIUtil.getDrawable(bgDrawable));
  }
    /**
     * 设置头部标题
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
     * 获取头部.
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }
    public void setToolBarLeft(int drawableID,String ex,int gone){
        if (null!=mLeftTitile){
            mLeftTitile.setText(ex);
            Drawable bgDrawable= UIUtil.getDrawable(drawableID);
/// 这一步必须要做,否则不会显示.  
            bgDrawable.setBounds(0, 0, bgDrawable.getMinimumWidth(),bgDrawable.getMinimumHeight());
            mLeftTitile.setCompoundDrawables(bgDrawable,null,null,null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLeftTitile.setBackground(UIUtil.getDrawable(drawableID));
            }
            mLeftTitile.setVisibility(gone);

        }
}

    /**
     * 版本号小于21的后退按钮图片
     */

    private void showBack(){
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加
        if(null!=mLeftTitile) {
            mLeftTitile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

    }
    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     * @return
     */
    protected boolean isShowBacking(){
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if(null != getToolbar() && isShowBacking()){
            showBack();
        }
    }


    /**
     * 微信支付Test
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
     * 初始化View
     */
    public void initView(){}

    /**
     * 关闭软键盘
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

}
