package com.shenyu.laikaword.module.us.address.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.adapter.ViewHolder;
import com.shenyu.laikaword.model.adapter.wrapper.EmptyWrapper;
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
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

public class SelectAddressActivity extends LKWordBaseActivity {


    @BindView(R.id.bt_ok_address)
    TextView btOkAddress;
    @BindView(R.id.re_cy_view)
    RecyclerView reCyView;
    private List<AddressReponse.PayloadBean> payload = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.activity_select_address;
    }

    EmptyWrapper emptyWrapper;

    @Override
    public void initView() {
        setToolBarTitle("选择地址");
        setToolBarRight("管理", 0);
        reCyView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(5), UIUtil.getColor(R.color.main_bg_gray)));
        reCyView.setLayoutManager(new LinearLayoutManager(this));
    }

    CommonAdapter<AddressReponse.PayloadBean> commonAdapter;
    Intent intent;
    Bundle bundle;
    static int selectedPosition =-1;

    @Override
    public void doBusiness(Context context) {
          intent = getIntent();
          bundle = new Bundle();
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
        initData();
        subscribeEvent();


    }

    @Override
    public void setupActivityComponent() {

    }


    @OnClick({R.id.rl_toolbar_subtitle,R.id.bt_ok_address})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.rl_toolbar_subtitle:
                IntentLauncher.with(this).launch(AddressInfoActivity.class);
                break;
            case R.id.bt_ok_address:
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_UPDATA_USER_ADDRESS:
                                initData();
                                break;
                        }
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
    public void initData() {
     final AddressReponse.PayloadBean payloadBean = (AddressReponse.PayloadBean) getIntent().getSerializableExtra("payloadBean");
        retrofitUtils.setLifecycleTransformer(this.bindToLifecycle()).addSubscription(RetrofitUtils.apiStores.getAddress(), new ApiCallback<AddressReponse>() {
            @Override
            public void onSuccess(AddressReponse model) {
                if (model.isSuccess()) ;
                {
                    if (model.getPayload()!=null&&model.getPayload().size()>0){
                        btOkAddress.setVisibility(View.VISIBLE);
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
                    }



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
    protected void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mRxSub);
    }
}
