package com.shenyu.laikaword.module.mine.wallet.withdraw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.base.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.module.mine.bankcard.ui.activity.SelectBankIDActivity;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * 余额提现
 */
public class WthdrawMoneyActivity extends LKWordBaseActivity {


    @BindView(R.id.et_tixian_num)
    EditText etTixianNum;
    @BindView(R.id.tv_account_yue)
    TextView tvAccountYue;
    @BindView(R.id.tv_all_tixian)
    TextView tvAllTixian;
    @BindView(R.id.tv_tixianing)
    TextView tvTixianing;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    String carID;

    @Override
    public int bindLayout() {
        return R.layout.activity_wthdraw_money;
    }

    @Override
    public void initView() {
        setToolBarTitle("余额提现");
    }
    String yue;
    @Override
    public void doBusiness(Context context) {
        Observable.combineLatest(RxTextView.textChanges(etTixianNum), RxTextView.textChanges(tvBankType), new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                return isJyan();
            }
        }).subscribe(new Action1<Boolean>() {
            @SuppressLint("NewApi")
            @Override
            public void call(Boolean o) {
                if (o) {
                    tvTixianing.setEnabled(true);
                    tvTixianing.setBackground(UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle_light));
                }else {
                    tvTixianing.setEnabled(false);
                    tvTixianing.setBackground(UIUtil.getDrawable(R.drawable.bg_gray_icon));
                }
            }
        });

         yue = getIntent().getStringExtra("acountyue");
        if (StringUtil.validText(yue))
        tvAccountYue.setText(yue);
        subscribeEvent();
    }

    /**
     * 判断空值
     */
    @SuppressLint("NewApi")
    public Boolean isJyan(){
        Boolean bool = false;
        String bankName = etTixianNum.getText().toString().trim();
        String bankStr = tvBankType.getText().toString().trim();
        if (StringUtil.validText(bankStr)&&StringUtil.validText(bankName)) {
            if (StringUtil.formatDouble(etTixianNum.getText().toString().trim())>StringUtil.formatDouble(yue)){
//                ToastUtil.showToastShort("余额不够");
                bool =false;
            }else{
                bool =true;
            }
        }
        else {
//            ToastUtil.showToastShort("请选择银行和请输入正确的金额");
            bool =false;
        }

        return bool;

    }
    @Override
    public void setupActivityComponent() {

    }

    @OnClick({R.id.tv_tixianing, R.id.tv_select_bank,R.id.tv_all_tixian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tixianing:
                DialogHelper.setInputDialog(mActivity, true,etTixianNum.getText().toString().trim()
                        , new DialogHelper.LinstenrText() {
                    @Override
                    public void onLintenerText(String passWord) {
                        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.withdrawMoney(etTixianNum.getText().toString().trim(), carID,passWord), new ApiCallback<BaseReponse>() {
                            @Override
                            public void onSuccess(BaseReponse model) {
                                if (model.isSuccess()) {
                                    ToastUtil.showToastShort("提现成功");
                                    RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                                } else {
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
                    }

                    @Override
                    public void onWjPassword() {

                    }
                }).show();

                break;
            case R.id.tv_select_bank:
                Intent intent = new Intent(this,SelectBankIDActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.tv_all_tixian:
                etTixianNum.setText(tvAccountYue.getText().toString().trim());
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            String text =null;
            if(bundle!=null)
                text=bundle.getString("bankName");
                carID = bundle.getString("carID");
                if (StringUtil.validText(text))
                tvBankType.setText(text);
        }
    }
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_UPDATA_USER:
                                LoginReponse loginReponse = Constants.getLoginReponse();
                                tvAccountYue.setText(loginReponse.getPayload().getMoney());
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
    protected void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mRxSub);
    }
}
