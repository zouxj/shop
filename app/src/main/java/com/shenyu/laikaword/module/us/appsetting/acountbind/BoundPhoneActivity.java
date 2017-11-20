package com.shenyu.laikaword.module.us.appsetting.acountbind;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.helper.SendMsgHelper;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * 绑定手机号
 */
public class BoundPhoneActivity extends LKWordBaseActivity {


    @BindView(R.id.et_use_phone)
    EditText etUsePhone;
    @BindView(R.id.et_user_msg_code)
    EditText etUserMsgCode;
    @BindView(R.id.tv_send_msg_code)
    TextView tvSendMsgCode;
    @BindView(R.id.bt_login)
    TextView btLogin;

    @Override
    public int bindLayout() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initView() {
        Observable<CharSequence> textPhone = RxTextView.textChanges(etUsePhone);
        Observable<CharSequence> textCode = RxTextView.textChanges(etUserMsgCode);
        Observable.combineLatest(textPhone, textCode, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                return StringUtil.validText(charSequence.toString()) && StringUtil.validText(charSequence2.toString());
            }
        }).subscribe(new Action1<Boolean>() {
            @SuppressLint("NewApi")
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
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
        });
        setToolBarTitle("绑定手机号码");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }

    @OnClick({R.id.tv_send_msg_code, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_msg_code:
                SendMsgHelper.sendMsg(this.bindToLifecycle(),tvSendMsgCode,etUsePhone.getText().toString().trim(),"bindPhone");
                break;
            case R.id.bt_login:
                //TODO 绑定手机号码
                String code = etUserMsgCode.getText().toString().trim();
               final String phone =etUsePhone.getText().toString().trim();
                RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.bindPhone(phone, code), new ApiCallback<BaseReponse>() {
                    @Override
                    public void onSuccess(BaseReponse model) {
                            if (model.isSuccess()){
                                refreshUser();
                                IntentLauncher.with(BoundPhoneActivity.this).put("phone",phone).launchFinishCpresent(MainActivity.class);
//                                RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));

//                                    IntentLauncher.with(BoundPhoneActivity.this).launchFinishCpresent(AcountBdingSuccessActivity.class);
//                                    DialogHelper.makeUpdate(mActivity, "温馨提示", "绑定手机成功,请前往设置支付密码", "取消", "去设置", true, new DialogHelper.ButtonCallback() {
//                                        @Override
//                                        public void onNegative(Dialog dialog) {
//                                            IntentLauncher.with(BoundPhoneActivity.this).put("phone",phone).launchFinishCpresent(SetPassWordMsgCodeActivity.class);
//                                        }
//
//                                        @Override
//                                        public void onPositive(Dialog dialog) {
//
//
//                                        }
//                                    }).show();


                            }else {
                                ToastUtil.showToastShort(model.getError().getMessage());
                            }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
                break;
        }
    }
}
