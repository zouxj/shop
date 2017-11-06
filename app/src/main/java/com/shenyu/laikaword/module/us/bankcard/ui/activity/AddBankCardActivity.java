package com.shenyu.laikaword.module.us.bankcard.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.Interactor.IOptionPickerVierCallBack;
import com.shenyu.laikaword.di.module.MineModule;
import com.shenyu.laikaword.module.us.bankcard.presenter.AddBankPresenter;
import com.shenyu.laikaword.module.us.bankcard.view.AddBankView;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func5;

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends LKWordBaseActivity implements AddBankView {

    @BindView(R.id.et_add_bank_card_num)
    EditText etAddBankCardNum;
    @BindView(R.id.et_add_bank_yinhang)
    EditText etAddBankYinhang;
    @BindView(R.id.et_add_bank_address)
    TextView etAddBankAddress;
    @Inject
    CityDataHelper cityDataHelper;
    @Inject
    AddBankPresenter addBankPresenter;
    @BindView(R.id.bt_add_bank)
    TextView btAddBank;
    @BindView(R.id.et_zhihang)
    EditText editTextZhang;
    @BindView(R.id.et_bank_user_name)
    EditText etUserName;
    String cardNum;
    String bankName;
    String bankZhangName;
    String bankProvince;
    String bankCity;
    String bankUserName;


    @Override
    public int bindLayout() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    public void initView() {
        setToolBarTitle("添加银行卡");
    }

    public void setMonitor() {
        Observable.combineLatest(RxTextView.textChanges(etAddBankCardNum), RxTextView.textChanges(etAddBankYinhang), RxTextView.textChanges(etUserName), RxTextView.textChanges(editTextZhang),
                RxTextView.textChanges(etAddBankAddress), new Func5<CharSequence, CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
                           return StringUtil.validText(charSequence.toString().trim()) && StringUtil.validText(charSequence2.toString().trim()) &&
                                StringUtil.validText(charSequence3.toString().trim()) && StringUtil.validText(charSequence4.toString().trim())&&StringUtil.validText(charSequence5.toString().trim())&&StringUtil.validText(charSequence.toString());
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

    }

    @Override
    public void doBusiness(Context context) {
        setMonitor();
    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this, this)).inject(this);
    }

    @OnClick({R.id.bt_add_bank, R.id.bt_select_address_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_bank:
                //TODO 保存下发
                    getValue();
                    bankNo(cardNum);
                    addBankPresenter.setAddRequest(this.bindToLifecycle(),cardNum, bankName, bankZhangName, bankUserName, bankProvince, bankCity);
                break;

            case R.id.bt_select_address_bank:
                heideSoftInput();
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
//            case R.id.tv_send_msg_code:
//                //获取手机验证码
//                String phone = etAddBankPhone.getText().toString().trim();
//                if (!StringUtil.isTelNumber(phone))
//                    ToastUtil.showToastShort("手机验证码无效");
//                else
//                addBankPresenter.sendMsg(phone, tvSendMsgCode);
//                break;

        }
    }
    private   boolean bankNo(String bankNO){
        if (StringUtil.checkBankCard(bankNO))
            return true;
            else {
                ToastUtil.showToastShort("请输入正确的银行卡号");
            return false;
        }
    }


    @Override
    public void isLoading() {
        loadViewHelper.showLoadingDialog(this);
    }

    @Override
    public void dataCountChanged(int count) {

    }

    @Override
    public void loadFinished() {
        loadViewHelper.closeLoadingDialog();
        RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_BANK, null));
        ToastUtil.showToastShort("添加成功");
        finish();
    }

    @Override
    public void loadFailure() {
        ToastUtil.showToastShort("添加失败");
        loadViewHelper.closeLoadingDialog();

    }

    //获取文本输入框值
    private void getValue() {
        cardNum = etAddBankCardNum.getText().toString().trim();
        bankName = etAddBankYinhang.getText().toString().trim();
        bankZhangName = editTextZhang.getText().toString().trim();
        bankUserName = etUserName.getText().toString().trim();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addBankPresenter.detachView();

    }
}