package com.shenyu.laikaword.module.us.address.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.module.BankModule;
import com.shenyu.laikaword.di.module.UserAddressModule;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.AddressReponse;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.address.presenter.AddressInfoPresenter;
import com.shenyu.laikaword.module.us.address.view.AddressInfoView;
import com.shenyu.laikaword.module.us.appsetting.UserInfoView;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

public class AddressInfoActivity extends LKWordBaseActivity implements AddressInfoView {

    @BindView(R.id.rl_address_list)
    RecyclerView rlAddressList;
    private CommonAdapter<AddressReponse.PayloadBean> commonAdapter;
    private List<AddressReponse.PayloadBean> payload;
    EmptyWrapper<AddressReponse.PayloadBean> emptyWrapper;
    @Inject
    AddressInfoPresenter addressInfoPresenter;
    @Override
    public int bindLayout() {
        return R.layout.activity_add_address_info;
    }

    @Override
    public void initView() {
        setToolBarTitle("我的地址");
        rlAddressList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(9),UIUtil.getColor(R.color.main_bg_gray)));
        rlAddressList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void doBusiness(Context context) {
        payload = new ArrayList<>();
        addressInfoPresenter.getAddressInfo(this.bindToLifecycle());
        commonAdapter = new CommonAdapter<AddressReponse.PayloadBean>(R.layout.item_mine_address_info,payload) {
            @Override
            protected void convert(final ViewHolder holder, final AddressReponse.PayloadBean addressReponse, final int position) {
                holder.setText(R.id.tv_address_name,addressReponse.getReceiveName());
                holder.setText(R.id.tv_address_tel,addressReponse.getPhone());

                holder.setText(R.id.tv_select_adress, addressReponse.getProvince()+addressReponse.getCity()+addressReponse.getDetail());
                holder.setOnClickListener(R.id.tv_to_edite_address, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IntentLauncher.with(AddressInfoActivity.this).putObjectString("AddressInfo",addressReponse).launch(EditAddressActivity.class);
                    }
                });

                CheckBox checkBox = holder.getView(R.id.ck_moren);
                checkBox.setChecked((addressReponse.getDefaultX()==1));
                TextView tvMoren= holder.getView(R.id.tv_check_more);
                if (checkBox.isChecked())
                    tvMoren.setTextColor(UIUtil.getColor(R.color.app_theme_red));
                    else
                    tvMoren.setTextColor(UIUtil.getColor(R.color.color_999));
                holder.setOnClickListener(R.id.tv_delete_address, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        addressInfoPresenter.deleteAddress(AddressInfoActivity.this,AddressInfoActivity.this.bindToLifecycle(),addressReponse);

                    }
                });
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (addressReponse.getDefaultX()==1){
                            addressInfoPresenter.setAddress(AddressInfoActivity.this.bindToLifecycle(),addressReponse,0);
                        }else if (addressReponse.getDefaultX()==0){
                            addressInfoPresenter.setAddress(AddressInfoActivity.this.bindToLifecycle(),addressReponse,1);
                        }
                    }
                });
            }
        };
         emptyWrapper = new EmptyWrapper(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.address_empty));
        rlAddressList.setAdapter(emptyWrapper);
    }



    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new UserAddressModule(this)).inject(this);
    }
    @OnClick(R.id.tv_add_address)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_add_address:
                IntentLauncher.with(this).put("Type","ADD").launch(EditAddressActivity.class);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mRxSub);
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
    public void loadFailure() {

    }

    @Override
    public void subscribeEvent(Event myEvent) {
        switch (myEvent.event) {
            case EventType.ACTION_UPDATA_USER_ADDRESS:
                addressInfoPresenter.getAddressInfo(this.bindToLifecycle());
                break;
        }
    }

    @Override
    public void setAddress(BaseReponse baseReponse) {

    }

    @Override
    public void getAddressInfo(AddressReponse baseReponse) {
        payload.clear();
        payload.addAll(baseReponse.getPayload());
        emptyWrapper.notifyDataSetChanged();
    }
}
