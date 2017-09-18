package com.shenyu.laikaword.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.interfaces.IBaseActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxj.parlibary.alipay.AliPayAPI;
import com.zxj.parlibary.alipay.AliPayReq2;
import com.zxj.parlibary.qqpay.QQPayAPI;
import com.zxj.parlibary.qqpay.QQPayReq;
import com.zxj.parlibary.resultlistener.OnAliPayListener;
import com.zxj.parlibary.resultlistener.OnWechatPayListener;
import com.zxj.parlibary.resultlistener.QqPayListener;
import com.zxj.parlibary.wechatpay.WechatPayAPI;
import com.zxj.parlibary.wechatpay.WechatPayReq;
import com.zxj.utilslibrary.utils.ActivityManageUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/8/2 0002.
 */

public abstract class LKWordBaseActivity extends AppCompatActivity implements IBaseActivity{
    protected final String TAG = getClass().getSimpleName();
    protected Activity mActivity;
    private TextView mToolbarTitle;
    protected TextView mToolbarSubTitle;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( bindLayout());
        mActivity = this;
        ButterKnife.bind(this);
        setupActivityComponent();
        ActivityManageUtil.getAppManager().addActivity(this);
        initToolBar();
        initView();
        doBusiness(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManageUtil.getAppManager().finishActivity(this);
    }

  public void  initToolBar(){
      mToolbar = (Toolbar) findViewById(R.id.toolbar);
       /*
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("Sub Title");
        */
      mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
      mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
      if (mToolbar != null) {
          //将Toolbar显示到界面
          setSupportActionBar(mToolbar);
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
  public void setToolBarRight(String rightTitle){
      mToolbarSubTitle.setText(rightTitle);
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

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack(){
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.mipmap.ic_launcher);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
     */
    public void testWechatPay(){
        String appid        = "";
        String partnerid    = "";
        String prepayid     = "";
        String noncestr     = "";
        String timestamp    = "";
        String sign         = "";
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(this) //activity实例
                .setAppId(appid) //微信支付AppID
                .setPartnerId(partnerid)//微信支付商户号
                .setPrepayId(prepayid)//预支付码
//				.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
                .setNonceStr(noncestr)
                .setTimeStamp(timestamp)//时间戳
                .setSign(sign)//签名
                .create();

        wechatPayReq.setOnWechatPayListener(new OnWechatPayListener() {
									@Override
									public void onPaySuccess(int errorCode) {
										ToastUtil.showToastShort("支付成功");

									}

									@Override
									public void onPayFailure(int errorCode) {
										ToastUtil.showToastShort("支付成功");

									}
								});
        WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
    }
    /**
     * 安全的支付宝支付测试
     */
    public void testAliPaySafely(){
        String partner          = "";
        String seller           = "";

        Activity activity       = this;
        String outTradeNo       = "";
        String price            = "";
        String orderSubject     = "";
        String orderBody        = "";
        String callbackUrl      = "";


        String rawAliOrderInfo = new AliPayReq2.AliOrderInfo()
                .setPartner(partner) //商户PID || 签约合作者身份ID
                .setSeller(seller)  // 商户收款账号 || 签约卖家支付宝账号
                .setOutTradeNo(outTradeNo) //设置唯一订单号
                .setSubject(orderSubject) //设置订单标题
                .setBody(orderBody) //设置订单内容
                .setPrice(price) //设置订单价格
                .setCallbackUrl(callbackUrl) //设置回调链接
                .createOrderInfo(); //创建支付宝支付订单信息


        //TODO 这里需要从服务器获取用商户私钥签名之后的订单信息
//        String signAliOrderInfo = getSignAliOrderInfoFromServer(rawAliOrderInfo);
        String signAliOrderInfo = null;
        AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                .with(activity)//Activity实例
                .setRawAliPayOrderInfo(rawAliOrderInfo)//set the ali pay order info
                .setSignedAliPayOrderInfo(signAliOrderInfo) //set the signed ali pay order info
                .create()//
                .setOnAliPayListener(new OnAliPayListener() {
                    @Override
                    public void onPaySuccess(String resultInfo) {
                        
                    }

                    @Override
                    public void onPayFailure(String resultInfo) {

                    }

                    @Override
                    public void onPayConfirmimg(String resultInfo) {

                    }

                    @Override
                    public void onPayCheck(String status) {

                    }
                });//
        AliPayAPI.getInstance().sendPayReq(aliPayReq);

    }

//    /**
//     * 获取签名后的支付宝订单信息  (用商户私钥RSA加密之后的订单信息)
//     * @param rawAliOrderInfo
//     * @return
//     */
//    private String getSignAliOrderInfoFromServer(String rawAliOrderInfo) {
//        return null;
//    }

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
    QQPayReq qqPayReq;
    public void testQQPay(){
         String appId="";
         String  nonce=String.valueOf(System.currentTimeMillis());;
         long  timeStamp =System.currentTimeMillis() / 1000;
         String  tokenId="";
         String  pubAcc="";
         String  pubAccHint="";
         String  bargainorId="";
         String  sigType="";
         String  sig="";
        QQPayReq qqPayReq = new QQPayReq.Builder()
                .with(this) //activity实例
                .setAppId(appId)
                .setTokenId(tokenId)
                .setBargainorId(bargainorId)
                .setNonce(nonce)
                .setPubAcc(pubAcc)
                .setPubAccHint(pubAccHint)
                .setTimeStamp(timeStamp)
                .setSigType(sigType)
                .setSig(sig)
                .create();
        QQPayAPI.getInstance().sendPayReq(qqPayReq, new QqPayListener() {
            @Override
            public void onPaySuccess(int successCode) {

            }

            @Override
            public void onPayFailure(int errorCode) {

            }
        });
    }

    /**
     * 微信登录
     */
    private String wxappid = "";
    protected void setWXLogin() {
        IWXAPI api = WXAPIFactory.createWXAPI(this, "wxappid", true);
        api.registerApp(wxappid);
        if (api != null && api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        } else {
            ToastUtil.showToastLong("您尚未安装微信");
        }
    }
    /**
     * @RuntimePermissions	✓	注解在其内部需要使用运行时权限的Activity或Fragment上
     @NeedsPermission	✓	注解在需要调用运行时权限的方法上，当用户给予权限时会执行该方法
     @OnShowRationale		注解在用于向用户解释为什么需要调用该权限的方法上，只有当第一次请求权限被用户拒绝，下次请求权限之前会调用
     @OnPermissionDenied		注解在当用户拒绝了权限请求时需要调用的方法上
     @OnNeverAskAgain		注解在当用户选中了授权窗口中的不再询问复选框后并拒绝了权限请求时需要调用的方法，一般可以向用户解释为何申请此权限，
     并根据实际需求决定是否再次弹出权限请求对话框
     */
    /**
     * 初始化View
     */
    public void initView(){}
}
