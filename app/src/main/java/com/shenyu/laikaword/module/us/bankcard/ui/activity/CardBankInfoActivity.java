package com.shenyu.laikaword.module.us.bankcard.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.adapter.ReslerAdapter;
import com.shenyu.laikaword.model.adapter.ViewHolder;
import com.shenyu.laikaword.model.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.BankInfoReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PickUpSuccessActivity;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PickUpTelActivity;
import com.shenyu.laikaword.ui.view.widget.DeleteRecyclerView;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import com.shenyu.laikaword.helper.DialogHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

public class CardBankInfoActivity extends LKWordBaseActivity {

    @BindView(R.id.card_cy_list)
    DeleteRecyclerView recyclerView;
     ReslerAdapter reslerAdapter ;
    EmptyWrapper emptyWrapper;
    private List<BankInfoReponse.PayloadBean> payload=new ArrayList<>();
    @Override
    public int bindLayout() {
        return R.layout.activity_card_bank_info;
    }

    @Override
    public void initView() {
        setToolBarTitle("银行卡");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(1),UIUtil.getColor(R.color.main_bg_gray)));
//        commonAdapter = new CommonAdapter<BankInfoReponse.PayloadBean>(R.layout.item_cardinfo_list,payload) {
//            @Override
//            protected void convert(ViewHolder holder, final BankInfoReponse.PayloadBean payloadBean, final int position) {
//                holder.setText(R.id.tv_bank_no,StringUtil.formatBankNumber(payloadBean.getCardNo()));
//                holder.setText(R.id.tv_card_bank,payloadBean.getBankName());
//                holder.setOnClickListener(R.id.tv_card_num, new  View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        DialogHelper.deleteBankDialog(CardBankInfoActivity.this, "解除绑定后银行卡服务将不可使用,包括快捷支付","解除绑定",new DialogHelper.ButtonCallback() {
//                            @Override
//                            public void onNegative(Dialog dialog) {
//                                if (deleteBank(payloadBean.getCardId())) {
//                                    payload.remove(position);
//                                    emptyWrapper.notifyDataSetChanged();
//                                }
//                            }
//
//                            @Override
//                            public void onPositive(Dialog dialog) {
//
//                            }
//                        });
//                    }
//                });
//            }
//        };

        reslerAdapter = new ReslerAdapter(UIUtil.getContext(),payload);
        emptyWrapper = new EmptyWrapper(reslerAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.bank_empty));
        recyclerView.setAdapter(emptyWrapper);
        recyclerView.setOnItemClickListener(new DeleteRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onDeleteClick(final int position) {
                DialogHelper.deleteBankDialog(CardBankInfoActivity.this, "解除绑定后银行卡服务将不可使用,包括快捷支付","解除绑定",new DialogHelper.ButtonCallback() {
                    @Override
                    public void onNegative(Dialog dialog) {

                        deleteBank(payload.get(position).getCardId());
                    }

                    @Override
                    public void onPositive(Dialog dialog) {

                    }
                });

            }

        });
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
        retrofitUtils.setLifecycleTransformer(this.bindToLifecycle()).addSubscription(RetrofitUtils.apiStores.getBankCard(), new ApiCallback<BankInfoReponse>() {
            @Override
            public void onSuccess(BankInfoReponse model) {
                if (model.isSuccess()) {
                    payload.clear();
                    payload.addAll(model.getPayload());
                    emptyWrapper.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
        emptyWrapper.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_add_bank)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_add_bank:
                toAddBank();
                break;
        }
}



    @Override
    public void setupActivityComponent() {

    }
    Boolean deleteBoolean=true;
    private boolean deleteBank(String cardID){
        RetrofitUtils.getRetrofitUtils().setLifecycleTransformer(this.bindToLifecycle()).addSubscription(RetrofitUtils.apiStores.deleteBankCard(cardID), new ApiCallback<BaseReponse>() {
            @Override
            public void onSuccess(BaseReponse model) {
                if (model.isSuccess()) {
                    ToastUtil.showToastShort("成功删除");
                    initData();
                    deleteBoolean = true;
                } else {
                    deleteBoolean = false;
                }
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
        RxSubscriptions.remove(mRxSub);
    }
}
