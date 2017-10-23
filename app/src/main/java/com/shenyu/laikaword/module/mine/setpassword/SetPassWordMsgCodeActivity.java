package com.shenyu.laikaword.module.mine.setpassword;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.bean.reponse.MsgCodeReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.SendMsgHelper;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 设置密码获取验证码
 */
public class SetPassWordMsgCodeActivity extends LKWordBaseActivity {

    @BindView(R.id.tv_ms_code)
    TextView tvMsCode;
    @BindView(R.id.tv_send_msg_phone)
    TextView tvSendMsgPhone;
    @BindView(R.id.tv_msg_code)
    EditText tvMsgCode;
    @BindView(R.id.tv_down_time)
    TextView tvDownTime;
    String typeActivity;
    @Override
    public int bindLayout() {
        return R.layout.activity_set_pass_word_msg_code;
    }

    @Override
    public void initView() {
         typeActivity= getIntent().getStringExtra("RESERT");
        setToolBarTitle("发送验证码");
        tvMsCode.setText(Html.fromHtml("我们已发送<b>验证码<b/>到您的手机"));
    }

    @Override
    public void doBusiness(Context context) {

            LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
            if (null!=loginReponse) {
            final String phone = loginReponse.getPayload().getBindPhone();
            if (StringUtil.validText(phone)) {
                tvSendMsgPhone.setText(Html.fromHtml("<b>" + StringUtil.formatPhoneNumber(phone) + "</b>"));
            }else {
                String phones =getIntent().getStringExtra("phone");
                if (StringUtil.validText(phones)){
                    tvSendMsgPhone.setText(Html.fromHtml("<b>" + StringUtil.formatPhoneNumber(phones) + "</b>"));
                }
            }
            SendMsgHelper.sendMsg(tvDownTime,phone,"setTransactionPIN");
            tvDownTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendMsgHelper.sendMsg(tvDownTime,phone,"setTransactionPIN");
                }
            });
        }

        RxTextView.textChanges(tvMsgCode).subscribe(new Action1<CharSequence>() {

            @Override
            public void call(CharSequence charSequence) {
                if (charSequence.toString().length()==6){
                    RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.validateSMSCode("validateSMSCode", charSequence.toString()), new ApiCallback<MsgCodeReponse>() {
                        @Override
                        public void onSuccess(MsgCodeReponse model) {
                            if (model.isSuccess())
                                if (typeActivity != null && typeActivity.equals("RESERT")) {
                                    IntentLauncher.with(mActivity).put("typeActivity", typeActivity).put("codeToken", model.getPayload().getSMSToken()).launch(SetPassWordOneActivity.class);
                                    finish();
                                } else {
                                    IntentLauncher.with(mActivity).put("codeToken", model.getPayload().getSMSToken()).launch(SetPassWordOneActivity.class);
                                    finish();
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
            }
        });

    }

    @Override
    public void setupActivityComponent() {

    }

}
