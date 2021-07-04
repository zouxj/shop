package com.shenyu.laikaword.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.goods.order.ShopSuccessActivity;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LaiKaApplication.iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Toast.makeText(this, "onReq===>>>get baseReq.getType : "+baseReq.getType(), Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LaiKaApplication.iwxapi.handleIntent(intent, this);
    }


    @Override
    public void onResp(BaseResp resp) {
//        0	成功	展示成功页面
//        -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//        -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
        if(resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            switch (resp.errCode){
                case BaseResp.ErrCode.ERR_OK:
                    if (Constants.PAY_WX_TYPE==1) {
                        ToastUtil.showToastShort("充值成功");
                        RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                        RxBus.getDefault().post(new Event(EventType.ACTION_PAY_WEIXIN, null));
                        finish();
                    }else if (Constants.PAY_WX_TYPE==2){
                        ToastUtil.showToastShort("支付成功");
                        RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                        IntentLauncher.with(this).launchFinishCpresent(ShopSuccessActivity.class);
                    }
                    break;
                case -1:
                    ToastUtil.showToastShort("支付失败");
                    finish();
                    break;
                case -2:
                    ToastUtil.showToastShort("支付取消");
                    finish();
                    break;
            }
        }
    }
}
