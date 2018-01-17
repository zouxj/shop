package com.shenyu.laikaword.module.us.address.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.UserAddressModule;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.AddressReponse;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.Interactor.IOptionPickerVierCallBack;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.address.presenter.EditAddressPresenter;
import com.shenyu.laikaword.module.us.address.view.EditAddressView;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func4;

public class EditAddressActivity extends LKWordBaseActivity implements EditAddressView {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.edite_address)
    TextView editeAddress;
    @BindView(R.id.edit_address_detail)
    EditText editAddressDetail;
    @BindView(R.id.cb_select_address)
    CheckBox cbSelectAddress;
    String useName;
    String tel;
    String address;
    String addressDetail;
    CityDataHelper cityDataHelper;
    Map<String, String> mapParam;
    AddressReponse.PayloadBean payloadBean;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @Inject
    EditAddressPresenter editAddressPresenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void initView() {
        setToolBarTitle("编辑地址");
    }

    @Override
    public void doBusiness(Context context) {
        mapParam = new HashMap<>();
        String type = getIntent().getStringExtra("Type");

        if (null != type && type.equals("ADD")) {
            setToolBarTitle("地址信息");
        }
        if (getIntent() != null)
            payloadBean = (AddressReponse.PayloadBean) getIntent().getSerializableExtra("AddressInfo");



        cityDataHelper = new CityDataHelper(this,3);

        if (payloadBean != null) {
            editName.setText(payloadBean.getReceiveName());
            editTel.setText(payloadBean.getPhone());
            editeAddress.setText(payloadBean.getProvince() + payloadBean.getCity());
            editAddressDetail.setText(payloadBean.getDetail());
            cbSelectAddress.setChecked(payloadBean.getDefaultX() == 1);
            mapParam.put("addressId", payloadBean.getAddressId());
            mapParam.put("province", payloadBean.getProvince());
            mapParam.put("city", payloadBean.getCity());
            mapParam.put("district", payloadBean.getDetail());

        }
        setMonitor();

    }
    private void  setMonitor() {
        Observable.combineLatest( RxTextView.textChanges(editName), RxTextView.textChanges(editTel), RxTextView.textChanges(editAddressDetail), RxTextView.textChanges(editeAddress),
                new Func4<CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
                return StringUtil.validText(charSequence.toString().trim()) && StringUtil.validText(charSequence2.toString().trim()) &&
                        StringUtil.validText(charSequence3.toString().trim()) && StringUtil.validText(charSequence4.toString().trim());
            }

        }).subscribe(new Action1<Boolean>() {
            @SuppressLint("NewApi")
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    tvSave.setEnabled(true);
                    tvSave.setBackgroundColor(UIUtil.getColor(R.color.app_theme_red));
                } else {
                    tvSave.setEnabled(false);
                    tvSave.setBackgroundColor(UIUtil.getColor(R.color.app_theme_red_40));
                }
            }
        });
    }
    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new UserAddressModule(this)).inject(this);
    }

    @OnClick({R.id.tv_select_address, R.id.cb_select_address, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_address:
                //TODO 选择地址
                if (KeyBoardUtil.isActive(this))
                    heideSoftInput();
                cityDataHelper.ShowPickerView(new IOptionPickerVierCallBack() {
                    @Override
                    public void callBack(String shen, String shi, String xian) {
                        editeAddress.setText(shen+shi+xian);
                        mapParam.put("province", shen);
                        mapParam.put("city", shi);
                        mapParam.put("district", xian);
                    }
                });
                break;
            case R.id.cb_select_address:
                //TODO 设置默认

                break;
            case R.id.tv_save:
                //TODO 设置保存
                    //TODO 上传服务器
                useName = editName.getText().toString().trim();
                tel = editTel.getText().toString().trim();
                address = editeAddress.getText().toString().trim();
                addressDetail = editAddressDetail.getText().toString().trim();
                mapParam.put("phone", tel);
                mapParam.put("receiveName", useName);
                mapParam.put("detail", addressDetail);
                mapParam.put("default", (cbSelectAddress.isChecked() ? 1 : 0) + "");
                editAddressPresenter.setAddress(this.bindToLifecycle(),mapParam);
                break;
        }
    }


    @Override
    public void isLoading() {

    }

    @Override
    public void loadSucceed(BaseReponse baseReponse) {
        if (baseReponse.isSuccess()) {
            ToastUtil.showToastShort("修改成功");
            RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_ADDRESS, null));
            finish();
        }else
            ToastUtil.showToastShort(baseReponse.getError().getMessage());
    }


    @Override
    public void loadFailure() {

    }

}
