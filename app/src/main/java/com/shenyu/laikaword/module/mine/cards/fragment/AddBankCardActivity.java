package com.shenyu.laikaword.module.mine.cards.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.interfaces.IOptionPickerVierCallBack;
import com.shenyu.laikaword.module.mine.MineModule;
import com.shenyu.laikaword.module.mine.cards.AddBankPresenter;
import com.shenyu.laikaword.module.mine.cards.AddBankView;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import com.zxj.utilslibrary.widget.countdownview.VerificationCodeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends LKWordBaseActivity implements AddBankView {

    @BindView(R.id.et_add_bank_name)
    EditText etAddBankName;
    @BindView(R.id.et_add_bank_card_num)
    EditText etAddBankCardNum;
    @BindView(R.id.et_add_bank_yinhang)
    TextView etAddBankYinhang;
    @BindView(R.id.et_add_bank_address)
    TextView etAddBankAddress;
    @BindView(R.id.et_add_bank_zhihang)
    EditText etAddBankZhihang;
    @BindView(R.id.et_add_bank_phone)
    TextView etAddBankPhone;
    @BindView(R.id.et_add_bank_code)
    EditText etAddBankCode;
    @BindView(R.id.tv_get_msg_code)
    TextView etgetMsgCode;
    @Inject
    CityDataHelper cityDataHelper;
    @Inject
    AddBankPresenter addBankPresenter;


    @Override
    public int bindLayout() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    public void initView() {
        addBankPresenter.setMonitor(etAddBankName,etAddBankCardNum);
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this,this)).inject(this);
    }
    @OnClick({R.id.bt_add_bank,R.id.et_add_bank_address,R.id.tv_get_msg_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_add_bank:
                //TODO 保存下发
                addBankPresenter.setAddRequest();
                break;
            case R.id.et_add_bank_address:
                cityDataHelper.ShowPickerView(new IOptionPickerVierCallBack() {
                    @Override
                    public void callBack(String shen, String shi, String xian, String msg) {
                        etAddBankAddress.setText(msg);
                    }
                });
                //TODO 开户选择地址
                break;
            case R.id.tv_get_msg_code:
                //获取手机验证码
                if (!StringUtil.isTelNumber("13266834341"))
                    ToastUtil.showToastShort("手机验证码无效");
                addBankPresenter.sendMsg("1231231",etgetMsgCode);
                break;

        }
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void dataCountChanged(int count) {

    }

    @Override
    public void loadFinished() {

    }

    @Override
    public void setBankName(String bankName) {
                //获取手机验证码
    }

    @Override
    public void setMsgCode(String code) {
        ToastUtil.showToastShort(code);
        //获取到验证码
    }
}
