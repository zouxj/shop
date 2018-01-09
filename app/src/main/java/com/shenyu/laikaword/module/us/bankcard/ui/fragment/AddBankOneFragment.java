package com.shenyu.laikaword.module.us.bankcard.ui.fragment;

import android.app.Dialog;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.model.bean.reponse.BankReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

public class AddBankOneFragment extends IKWordBaseFragment {
    @BindView(R.id.et_bank_user_name)
    EditText etBankUserName;
    @BindView(R.id.et_add_bank_card_num)
    EditText etAddBankCardNum;
    @BindView(R.id.bt_add_bank)
    TextView btAddBank;
    @Override
    public int bindLayout() {
        return R.layout.fragment_add_bank_one;
    }

    @Override
    public void doBusiness() {
        setMonitor();
    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {

    }
    public void setMonitor() {
        Observable.combineLatest(RxTextView.textChanges(etAddBankCardNum), RxTextView.textChanges(etBankUserName), new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                return StringUtil.validText(charSequence.toString().trim())  &&
                        StringUtil.validText(charSequence2.toString().trim());
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    btAddBank.setEnabled(true);
                    btAddBank.setBackgroundColor(UIUtil.getColor(R.color.app_theme_red));
                } else {
                    btAddBank.setEnabled(false);
                    btAddBank.setBackgroundColor(UIUtil.getColor(R.color.app_theme_red_40));
                }
            }
        });

    }

    @OnClick(R.id.bt_add_bank)
    public void onViewClicked() {
        if (StringUtil.validText(etAddBankCardNum.getText().toString().trim())&&etAddBankCardNum.getText().toString().trim().length()>=16)
        getCardAccountInfo();
    }

    //根据银行卡获取银行卡详情
    public void getCardAccountInfo() {
        //TODO 添加银行卡信息
        retrofitUtils.setLifecycleTransformer(this.bindToLifecycle()).addSubscription(retrofitUtils.apiStores.getCardAccountInfo(etAddBankCardNum.getText().toString().trim()), new ApiCallback<BankReponse>() {
            @Override
            public void onSuccess(BankReponse model) {
                if (model.isSuccess()) {
                    model.getPayload().setNickName(etBankUserName.getText().toString().trim());
                    RxBus.getDefault().post(new Event(EventType.ACTION_ADDBANK, model));
                }
                else
                    DialogHelper.tDialog(getActivity(), model.getError().getMessage(), "确定", new DialogHelper.ButtonCallback() {
                        @Override
                        public void onNegative(Dialog dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void onPositive(Dialog dialog) {

                        }
                    }).show();

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


}
