package com.zxj.parlibary.qqpay;

import android.app.Activity;
import android.util.Base64;

import com.tencent.mobileqq.openpay.api.IOpenApi;
import com.tencent.mobileqq.openpay.api.IOpenApiListener;
import com.tencent.mobileqq.openpay.api.OpenApiFactory;
import com.tencent.mobileqq.openpay.data.base.BaseResponse;
import com.tencent.mobileqq.openpay.data.pay.PayApi;
import com.tencent.mobileqq.openpay.data.pay.PayResponse;
import com.zxj.parlibary.resultlistener.QqPayListener;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class QQPayReq implements IOpenApiListener {

    private Activity activity;
    private String appId;
    private String  nonce;
    private long  timeStamp;
    private String  tokenId;
    private String  pubAcc;
    private String  pubAccHint;
    private String  bargainorId;
    private String  sigType;
    private String  sig;
    PayApi payApi;
    IOpenApi openApi;
    String callbackScheme = "qwallet100619284";
    public QQPayReq(){
        super();
    }

    /**
     * 发送QQ支付请求
     */
    public void send() {
        openApi = OpenApiFactory.getInstance(activity, appId);
        openApi.handleIntent(activity.getIntent(), this);
        payApi = new PayApi();
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

        try {
            signApi(payApi);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (payApi.checkParams()) {
            openApi.execApi(payApi);
        }

    }
    /**
     * 参数生成
     */
    public static class Builder{
//        String appId ,应用唯一id
//        String nonce 随机串，随机字段串每次请求都要不一样
//        long  timeStamp 时间戳，时间戳，为1970年1月1日00:00到请求发起时间的秒数
//        String  tokenId QQ钱包的预支付会话标识，具体值请传入调用统一下单接口后返回的“prepay_id”的值 注：参与签名参数key为“tokenId”,如“tokenId=8888888”
//        String  pubAcc 手Q公众帐号，暂时未对外开放申请。 注：所有参与签名的参数，如果value为空, 生成格式如“pubAcc=”
//        String  pubAccHint 关注手Q公众帐号提示语
//        String  bargainorId  QQ钱包支付商户号，具体值请传入调用统一下单接口时传入的“mch_id” 的值 注：参与签名参数key为“bargainorId”
//        String sigType 加密方式，签名时，使用的加密算法。目前只支持"HMAC-SHA1"，因此该参数填写"HMAC-SHA1"。
//        String  sig  签名串，参看“数字签名”

        private Activity activity;
        private String appId;
        private String  nonce;
        private long  timeStamp;
        private String  tokenId;
        private String  pubAcc;
        private String  pubAccHint;
        private String  bargainorId;
        private String  sigType;
        private String  sig;
        public Builder() {
            super();
        }
        public Builder with(Activity activity){
            this.activity = activity;
            return this;
        }


        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setNonce(String nonce) {
            this.nonce = nonce;
            return this;
        }

        public Builder setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder setTokenId(String tokenId) {
            this.tokenId = tokenId;
            return this;
        }

        public Builder setPubAcc(String pubAcc) {
            this.pubAcc = pubAcc;
            return this;
        }

        public Builder setPubAccHint(String pubAccHint) {
            this.pubAccHint = pubAccHint;
            return this;
        }

        public Builder setBargainorId(String bargainorId) {
            this.bargainorId = bargainorId;
            return this;
        }

        public Builder setSigType(String sigType) {
            this.sigType = sigType;
            return this;
        }

        public Builder setSig(String sig) {
            this.sig = sig;
            return this;
        }

        public QQPayReq create(){
            QQPayReq qqPayReq = new QQPayReq();
            qqPayReq.activity = this.activity;
            qqPayReq.appId=this.appId;
            qqPayReq.bargainorId=this.bargainorId;
            qqPayReq.nonce=this.nonce;
            qqPayReq.pubAcc=this.pubAcc;
            qqPayReq.sig=this.sig;
            qqPayReq.tokenId=this.tokenId;
            qqPayReq.timeStamp=this.timeStamp;
            qqPayReq.pubAccHint=this.pubAccHint;
            qqPayReq.sigType=this.sigType;
            return qqPayReq;
        }

    }
    @Override
    public void onOpenResponse(BaseResponse response) {
        String title = "Callback from mqq";
        String message;
        if (response == null) {
            message = "response is null.";
            return;
        } else {
            if (response instanceof PayResponse) {
                PayResponse payResponse = (PayResponse) response;
                message = " apiName:" + payResponse.apiName
                        + " serialnumber:" + payResponse.serialNumber
                        + " isSucess:" + payResponse.isSuccess()
                        + " retCode:" + payResponse.retCode
                        + " retMsg:" + payResponse.retMsg
                        + " openId:+ payResponse.openId";
                if (null!=qqPayListener){
                    if (payResponse.isSuccess()) {
                        if (!payResponse.isPayByWeChat()) {
                            message += " transactionId:" + payResponse.transactionId
                                    + " payTime:" + payResponse.payTime
                                    + " callbackUrl:" + payResponse.callbackUrl
                                    + " totalFee:" + payResponse.totalFee
                                    + " spData:" + payResponse.spData;
                        }
                        qqPayListener.onPaySuccess(payResponse.retCode);
                    }else{
                        qqPayListener.onPayFailure(payResponse.retCode);
                        switch (payResponse.retCode){
                            case 0:
                                break;
                            case -1:
                                //TODO 用户主动放弃支付
                                break;
                            case -2:
                                //TODO 用户登录超时
                                break;
                            case -3:
                                //TODO 重复提交订单
                                break;
                            case -4:
                                //TODO 快速注册用户手机号不一致
                                break;
                            case -5:
                                //TODO 账户被冻结
                                break;

                            case -6:
                                //TODO 支付密码输入错误次数超过上限

                                break;
                            case -100:
                                //TODO 网络异常错误

                                break;
                            case -101:
                                //TODO 参数错误
                                break;
                            default:
                                //TODO 未知错误
                                break;


                        }
                    }
                }

            } else {
                message = "response is not PayResponse.";
            }
        }

//        Dialog alertDialog = new AlertDialog.Builder(this).
//                setTitle(title).
//                setMessage(message).
//                create();
//        alertDialog.show();
    }
    /**
     * 签名步骤建议不要在app上执行，要放在服务器上执行.
     */
    // 签名步骤建议不要在app上执行，要放在服务器上执行
    // appkey建议不要保存app
    final String APP_KEY = "d139ae6fb0175e5659dce2a7c1fe84d5";
    public void signApi(PayApi api) throws Exception {
        // 按key排序
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("appId=").append(api.appId);
        stringBuilder.append("&bargainorId=").append(api.bargainorId);
        stringBuilder.append("&nonce=").append(api.nonce);
        stringBuilder.append("&pubAcc=").append("");
        stringBuilder.append("&tokenId=").append(api.tokenId);

        byte[] byteKey = (APP_KEY+"&").getBytes("UTF-8");
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(byteKey, "HmacSHA1");
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance("HmacSHA1");
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] byteSrc = stringBuilder.toString().getBytes("UTF-8");
        // 完成 Mac 操作
        byte[] dst = mac.doFinal(byteSrc);
        // Base64
        api.sig = Base64.encodeToString(dst, Base64.NO_WRAP);
        api.sigType = "HMAC-SHA1";
    }
    /**
     * QQ支付监听
     * @author Administrator
     *
     */
    private QqPayListener qqPayListener;
    public QQPayReq  setQQPayListener(QqPayListener qqPayListener) {
        this.qqPayListener = qqPayListener;
        return this;
    }

}
