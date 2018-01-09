package com.shenyu.laikaword.module.us.bankcard.ui.fragment;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.Interactor.IOptionPickerVierCallBack;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.model.bean.reponse.BankReponse;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func3;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBankTwoFragment extends IKWordBaseFragment {


    @BindView(R.id.et_add_bank_yinhang)
    EditText etAddBankYinhang;
    @BindView(R.id.tv_biaoti)
    TextView tvBiaoti;
    @BindView(R.id.et_add_bank_address)
    TextView etAddBankAddress;
    @BindView(R.id.bt_select_address_bank)
    RelativeLayout btSelectAddressBank;
    @BindView(R.id.et_zhihang)
    EditText etZhihang;
    @BindView(R.id.bt_add_bank)
    TextView btAddBank;
    CityDataHelper cityDataHelper;
    String bankProvince;
    String bankCity;

    @Override
    public int bindLayout() {
        return R.layout.fragment_add_bank_two;
    }

    @Override
    public void doBusiness() {
        cityDataHelper=new CityDataHelper(getActivity());
        setMonitor();
    }
    @Override
    public void initView(View view) {
        if (null==bankReponse)
            return;
        if (bankReponse.isSuccess()&&bankReponse.getPayload()!=null){
            bankProvince=bankReponse.getPayload().getProvince();
            bankCity=bankReponse.getPayload().getCity();
            etAddBankYinhang.setText(bankReponse.getPayload().getBank());
            etAddBankAddress.setText(bankReponse.getPayload().getProvince()+bankReponse.getPayload().getCity());
        }
    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {

    }


    public void setMonitor() {
        Observable.combineLatest(RxTextView.textChanges(etAddBankAddress), RxTextView.textChanges(etAddBankYinhang), RxTextView.textChanges(etZhihang), new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                return StringUtil.validText(charSequence.toString().trim()) && StringUtil.validText(charSequence2.toString().trim()) &&
                        StringUtil.validText(charSequence3.toString().trim());
            }
        }).subscribe(new Action1<Boolean>() {
            @SuppressLint("NewApi")
            @Override
            public void call(Boolean bool) {
                if (bool) {
                    btAddBank.setEnabled(true);
                    btAddBank.setBackgroundColor(UIUtil.getColor(R.color.app_theme_red));
                } else {
                    btAddBank.setEnabled(false);
                    btAddBank.setBackgroundColor(UIUtil.getColor(R.color.app_theme_red_40));
                }

            }
        });
        RxView.clicks(btAddBank).throttleFirst(3, TimeUnit.SECONDS ).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setAddRequest();
            }
        });

    }



    @OnClick({R.id.bt_select_address_bank, R.id.bt_add_bank})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_select_address_bank:
                KeyBoardUtil.heideSoftInput(getActivity());
                cityDataHelper.ShowPickerView(new IOptionPickerVierCallBack() {
                    @Override
                    public void callBack(String shen, String shi, String xian, String msg) {
                        etAddBankAddress.setText(msg);
                        bankProvince = shen;
                        bankCity = shi;
                    }

                });
                //TODO 开户选择地址
                break;
            case R.id.bt_add_bank:

                break;
        }
    }
    Map<String,String> mapParam = new HashMap<>();
    public void setAddRequest(){
        //TODO 添加银行卡信息
        mapParam.put("name", bankReponse.getPayload().getNickName());
        mapParam.put("cardNo",bankReponse.getPayload().getBankcard());
        mapParam.put("bankName",bankReponse.getPayload().getBank());
        mapParam.put("bankDetail",etZhihang.getText().toString().trim());
        mapParam.put("bankLogo",bankReponse.getPayload().getLogo());
        mapParam.put("province",bankProvince);
        mapParam.put("city",bankCity);
        mapParam.put("default","0");
        retrofitUtils.setLifecycleTransformer(this.bindToLifecycle()).addSubscription(retrofitUtils.apiStores.setBankCard(mapParam), new ApiCallback<BaseReponse>() {
            @Override
            public void onSuccess(BaseReponse model) {
                if (model.isSuccess()) {
                    ToastUtil.showToastShort("银行卡添加成功");
                    RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_BANK, null));
                    RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                    getActivity().finish();
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

private BankReponse bankReponse;

    public void setBankInfo(BankReponse bankInfo) {
        this.bankReponse = bankInfo;
    }
}
