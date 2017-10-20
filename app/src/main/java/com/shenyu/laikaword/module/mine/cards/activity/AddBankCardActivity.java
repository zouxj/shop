package com.shenyu.laikaword.module.mine.cards.activity;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.interfaces.IOptionPickerVierCallBack;
import com.shenyu.laikaword.module.mine.MineModule;
import com.shenyu.laikaword.module.mine.cards.AddBankPresenter;
import com.shenyu.laikaword.module.mine.cards.AddBankView;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.et_add_bank_phone)
    TextView etAddBankPhone;
    @BindView(R.id.tv_send_msg_code)
    TextView tvSendMsgCode;
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
    @BindView(R.id.et_get_msg_code)
    EditText etGetMsgCode;
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
//        addBankPresenter.setMonitor(etAddBankYinhang, etAddBankCardNum);
    }

    @Override
    public void doBusiness(Context context) {
    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this, this)).inject(this);
    }

    @OnClick({R.id.bt_add_bank, R.id.bt_select_address_bank, R.id.tv_send_msg_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_bank:
                //TODO 保存下发
                if (yzCode())
                addBankPresenter.setAddRequest(cardNum, bankName,bankZhangName,bankUserName,bankProvince,bankCity);
              else
                ToastUtil.showToastShort("请完善信息");
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
            case R.id.tv_send_msg_code:
                //获取手机验证码
                String phone = etAddBankPhone.getText().toString().trim();
                if (!StringUtil.isTelNumber(phone))
                    ToastUtil.showToastShort("手机验证码无效");
                else
                addBankPresenter.sendMsg(phone, tvSendMsgCode);
                break;

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
        RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_BANK,null));
        ToastUtil.showToastShort("添加成功");
        finish();
    }

    @Override
    public void loadFailure() {
        ToastUtil.showToastShort("添加失败");
        loadViewHelper.closeLoadingDialog();

    }

    //检验空值
private Boolean yzCode(){
     cardNum = etAddBankCardNum.getText().toString().trim();
     bankName = etAddBankYinhang.getText().toString().trim();
      bankZhangName = editTextZhang.getText().toString().trim();
      bankUserName =etUserName.getText().toString().trim();
    if (StringUtil.validText(bankZhangName)&&StringUtil.validText(cardNum)&&StringUtil.validText(bankName))
        return true;
         return false;
}
}
