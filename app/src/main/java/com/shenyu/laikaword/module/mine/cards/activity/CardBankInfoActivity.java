package com.shenyu.laikaword.module.mine.cards.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.reponse.BankInfoReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.shenyu.laikaword.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.rxbus.RxSubscriptions;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import com.shenyu.laikaword.helper.DialogHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class CardBankInfoActivity extends LKWordBaseActivity {

    @BindView(R.id.card_cy_list)
    RecyclerView recyclerView;
    CommonAdapter commonAdapter;
    private List<BankInfoReponse.PayloadBean> payload=new ArrayList<>();
    @Override
    public int bindLayout() {
        return R.layout.activity_card_bank_info;
    }

    @Override
    public void initView() {
        setToolBarTitle("银行卡");
        setToolBarRight(null,R.mipmap.add_icon);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(1),UIUtil.getColor(R.color.main_bg_gray)));
        commonAdapter = new CommonAdapter<BankInfoReponse.PayloadBean>(R.layout.item_cardinfo_list,payload) {
            @Override
            protected void convert(ViewHolder holder, final BankInfoReponse.PayloadBean payloadBean, final int position) {

                holder.setText(R.id.tv_bank_no,StringUtil.formatBankNumber(payloadBean.getCardNo()));
                holder.setText(R.id.tv_card_bank,payloadBean.getBankName());
                holder.setOnClickListener(R.id.tv_card_num, new  View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogHelper.deleteBankDialog(CardBankInfoActivity.this, new DialogHelper.ButtonCallback() {
                            @Override
                            public void onNegative(Dialog dialog) {
                                if (deleteBank(payloadBean.getCardId())) {
                                    payload.remove(position);
                                    commonAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onPositive(Dialog dialog) {

                            }
                        });
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void doBusiness(Context context) {
        subscribeEvent();
        initData();
    }
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_UPDATA_USER_BANK:
                                initData();
                                break;
                        }
//            }
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
    protected void initData() {
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.getBankCard(), new ApiCallback<BankInfoReponse>() {
            @Override
            public void onSuccess(BankInfoReponse model) {
                if (model.isSuccess()) {
                    payload.clear();
                    payload.addAll(model.getPayload());
                    commonAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
        commonAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.toolbar_subtitle)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_subtitle:
                IntentLauncher.with(this).launch(AddBankCardActivity.class);
                break;
        }
}
    @Override
    public void setupActivityComponent() {

    }
    Boolean deleteBoolean=true;
    private boolean deleteBank(String cardID){
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.deleteBankCard(cardID), new ApiCallback<BaseReponse>() {
            @Override
            public void onSuccess(BaseReponse model) {
                if (model.isSuccess())
                    deleteBoolean = true;
                else
                    deleteBoolean=false;

            }

            @Override
            public void onFailure(String msg) {
                deleteBoolean = false;
            }

            @Override
            public void onFinish() {

            }
        });
        return deleteBoolean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
