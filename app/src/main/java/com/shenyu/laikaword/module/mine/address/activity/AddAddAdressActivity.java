package com.shenyu.laikaword.module.mine.address.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.interfaces.IOptionPickerVierCallBack;
import com.shenyu.laikaword.module.mine.MineModule;
import com.shenyu.laikaword.module.mine.address.AddPresenter;
import com.shenyu.laikaword.module.mine.address.AddressView;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * 添加地址
 */
public class AddAddAdressActivity extends LKWordBaseActivity implements AddressView {


    @BindView(R.id.et_add_address_phone)
    EditText editAddressPhone;
    @BindView(R.id.et_ad_address)
    EditText etAdAddress;
    @BindView(R.id.tv_add_address_sheng)
    TextView tvAddAddressSheng;
    @BindView(R.id.tv_add_address_jie)
    TextView tvAddAddressJie;
    @BindView(R.id.check_add_address)
    CheckBox checkAddAddress;
    @Inject
     CityDataHelper cityDataHelper;
    @Inject
    AddPresenter addPresenter;
    private String addressName;
    private String addressPhone;
    private String address;
    private String addJieDao;
    @Override
    public int bindLayout() {
        return R.layout.activity_add_adress;
    }

    @Override
    public void doBusiness(Context context) {
        String type = getIntent().getStringExtra("Type");
        if (null!=type&&type.equals("ADD")){
            setToolBarTitle("地址信息");
        }
    }

    @Override
    public void initView() {
        super.initView();
        setToolBarTitle("编辑地址");
        setToolBarRight("保存",0);

        RxView.clicks(mToolbarSubTitle).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Observer<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                addPresenter.requestData();
                addressName = etAdAddress.getText().toString().trim();
                addressPhone=editAddressPhone.getText().toString().trim();
                address=tvAddAddressSheng.getText().toString().trim();
                addJieDao= tvAddAddressJie.getText().toString().trim();
                if (StringUtil.isText(addressName)||StringUtil.isText(addressPhone)||
                        StringUtil.isText(address)||StringUtil.isText(addJieDao)){
                    addPresenter.requestData();
                }else {
                    ToastUtil.showToastShort("请把信息完善");
                }
            }
        });

    }
    @OnClick({R.id.tv_add_address_sheng})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_add_address_sheng:
                cityDataHelper.ShowPickerView(new IOptionPickerVierCallBack() {
                    @Override
                    public void callBack(String shen, String shi, String xian, String msg) {
                        tvAddAddressSheng.setText(msg);
                    }
                });
                break;
        }
        //预防多次点击

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this,this)).inject(this);
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void dataCountChanged(int count) {

    }

    @Override
    public void loadFinished() {
        ToastUtil.showToastShort("功能待开发...");
    }

}
