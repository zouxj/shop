package com.shenyu.laikaword.module.us.address.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.di.module.UserAddressModule;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.AddressReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.address.presenter.SelectAddressPresneter;
import com.shenyu.laikaword.module.us.address.view.SelectAddressView;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

public class SelectAddressActivity extends LKWordBaseActivity implements SelectAddressView {


    @BindView(R.id.bt_ok_address)
    TextView btOkAddress;
    @BindView(R.id.re_cy_view)
    RecyclerView reCyView;
    private List<AddressReponse.PayloadBean> payload = new ArrayList<>();
    @Inject
    SelectAddressPresneter selectAddressPresneter;
    @Override
    public int bindLayout() {
        return R.layout.activity_select_address;
    }
    AddressReponse.PayloadBean payloadBean;
    EmptyWrapper emptyWrapper;
    CommonAdapter<AddressReponse.PayloadBean> commonAdapter;
    Intent intent;
    Bundle bundle;
    int selectedPosition =-1;

    @Override
    public void initView() {
        setToolBarTitle("选择地址");
        setToolBarRight("管理", 0);
        reCyView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(5), UIUtil.getColor(R.color.main_bg_gray)));
        reCyView.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public void doBusiness(Context context) {
          intent = getIntent();
          bundle = new Bundle();
        selectAddressPresneter.requestAddress(this.bindToLifecycle(),payloadBean);
        payloadBean=  (AddressReponse.PayloadBean)intent.getSerializableExtra("payloadBean");
        commonAdapter = new CommonAdapter<AddressReponse.PayloadBean>(R.layout.item_select_address, payload) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void convert(final ViewHolder holder, final AddressReponse.PayloadBean payloadBean, final int position) {
                CheckBox checkBox = holder.getView(R.id.ck_select_address);
                holder.setText(R.id.tv_select_address, payloadBean.getProvince() + payloadBean.getCity() + payloadBean.getDetail());
                holder.setText(R.id.tv_select_phone, payloadBean.getPhone());
                holder.setText(R.id.tv_select_name, payloadBean.getReceiveName());
                checkBox.setChecked(selectedPosition == position);
                if (selectedPosition==-1){
                    btOkAddress.setEnabled(false);
                    btOkAddress.setBackgroundColor(UIUtil.getColor(R.color.main_bg_gray));
                }else {
                    btOkAddress.setEnabled(true);
                    btOkAddress.setBackgroundColor(UIUtil.getColor(R.color.app_theme_red));
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition != position) {
                            //先取消上个item的勾选状态
                            if (selectedPosition != -1) {
                                holder.itemView.setSelected(false);
                                emptyWrapper.notifyItemChanged(selectedPosition);
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            holder.itemView.setSelected(true);
                            emptyWrapper.notifyItemChanged(selectedPosition);
                            bundle.putSerializable("address", payloadBean);
                            intent.putExtras(bundle);
                        } else if (selectedPosition == position) {
                            selectedPosition = -1; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            bundle.clear();
                            emptyWrapper.notifyItemChanged(position);//刷新当前点击item
                        }


                    }
                });

            }
        };
        emptyWrapper = new EmptyWrapper<AddressReponse.PayloadBean>(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.bank_empty));
        reCyView.setAdapter(emptyWrapper);



    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new UserAddressModule(this)).inject(this);
    }


    @OnClick({R.id.rl_toolbar_subtitle,R.id.bt_ok_address})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.rl_toolbar_subtitle:
                IntentLauncher.with(this).launch(AddressInfoActivity.class);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        selectAddressPresneter.detachView();
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void loadSucceed(BaseReponse baseReponse) {
        AddressReponse model = (AddressReponse) baseReponse;
        if (model.isSuccess()) ;
        {
            if (model.getPayload()!=null&&model.getPayload().size()>0){
                btOkAddress.setVisibility(View.VISIBLE);
                btOkAddress.setText("确认");
                btOkAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                payload.clear();
                payload.addAll(model.getPayload());
                emptyWrapper.notifyDataSetChanged();

                for (int i = 0; i < payload.size(); i++) {
                    if (payloadBean!=null) {
                        if (payload.get(i).getAddressId().equals(payloadBean.getAddressId())) {
                            selectedPosition = i;
                            bundle.putSerializable("address", payload.get(i));
                            intent.putExtras(bundle);
                        }
                    }

                }
            }else {
                bundle.putSerializable("address", null);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                payload.clear();
                payload.addAll(model.getPayload());
                emptyWrapper.notifyDataSetChanged();
                btOkAddress.setVisibility(View.VISIBLE);
                btOkAddress.setText("添加新地址");
                btOkAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IntentLauncher.with(SelectAddressActivity.this).launch(EditAddressActivity.class);
                    }
                });
            }



        }
    }

    @Override
    public void loadFailure() {

    }

    @Override
    public void subscribeEvent(Event myEvent) {
        switch (myEvent.event) {
            case EventType.ACTION_UPDATA_USER_ADDRESS:
                selectAddressPresneter.requestAddress(this.bindToLifecycle(),payloadBean);
                break;
        }
    }
}
