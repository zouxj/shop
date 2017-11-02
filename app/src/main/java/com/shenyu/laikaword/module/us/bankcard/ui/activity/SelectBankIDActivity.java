package com.shenyu.laikaword.module.us.bankcard.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.shenyu.laikaword.model.bean.reponse.BankInfoReponse;
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
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 银行卡管理
 */
public class SelectBankIDActivity extends LKWordBaseActivity {

    @BindView(R.id.card_cy_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_commit_selector)
    TextView tvCommitSelectBank;
    private String carID;
    private String carName;
    private List<BankInfoReponse.PayloadBean> payload=new ArrayList<>();
    EmptyWrapper emptyWrapper;
    @Override
    public int bindLayout() {
        return R.layout.activity_usr_card;
    }
    int selectedPosition=-5;
    @Override
    public void initView() {

        setToolBarTitle("我的银行卡");
        setToolBarRight(null,R.mipmap.add_icon);
        mToolbarSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentLauncher.with(SelectBankIDActivity.this).launch(AddBankCardActivity.class);
            }
        });

        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,1, UIUtil.getColor(R.color.divider)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter commonAdapter=  new CommonAdapter<BankInfoReponse.PayloadBean>(R.layout.item_card_list,payload) {

            @Override
            protected void convert(final ViewHolder holder, final BankInfoReponse.PayloadBean payloadBean, final int position) {
                CheckBox checkBox = holder.getView(R.id.cb_bank_name);
                if (payloadBean.getCardId().equals(carID)){
                    selectedPosition=position;
                }
                if (selectedPosition==-1){
                    tvCommitSelectBank.setEnabled(false);
                    tvCommitSelectBank.setBackgroundColor(UIUtil.getColor(R.color.main_bg_gray));
                }else {
                    tvCommitSelectBank.setEnabled(true);
                    tvCommitSelectBank.setBackgroundColor(UIUtil.getColor(R.color.app_theme_red));
                }
                checkBox.setChecked(payloadBean.getCardId().equals(carID)&&selectedPosition==position);
                holder.setText(R.id.tv_card_bank,payloadBean.getBankName()+"("+ StringUtil.getBankNumber(payloadBean.getCardNo())+")");
                holder.setOnClickListener(R.id.item_ck, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition!=position){
                            //先取消上个item的勾选状态
                            if (selectedPosition!=-5) {
                                carID = null;
                                carName=null;
                                holder.itemView.setSelected(false);
                                emptyWrapper.notifyItemChanged(selectedPosition);
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            carID = payloadBean.getCardId();
                            carName=payloadBean.getBankName()+"("+ StringUtil.getBankNumber(payloadBean.getCardNo())+")";
                            holder.itemView.setSelected(true);
                            emptyWrapper.notifyItemChanged(selectedPosition);

                        }else if (selectedPosition==position){
                            carID = null;
                            carName=null;
                            selectedPosition = -5; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            emptyWrapper.notifyItemChanged(position);//刷新当前点击item
                        }
                    }
                });
            }
        };
         emptyWrapper = new EmptyWrapper(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.money_empty));
        recyclerView.setAdapter(emptyWrapper);
    }
    @OnClick(R.id.tv_commit_selector)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_commit_selector:
                Intent intent =getIntent();
                Bundle  bundle =new Bundle();
                bundle.putCharSequence("bankName",carName);
                bundle.putCharSequence("carID",carID);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
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
    @Override
    public void doBusiness(Context context) {
        initData();
        subscribeEvent();

    }

    @Override
    public void setupActivityComponent() {

    }

    protected void initData() {
       carID= getIntent().getStringExtra("carID");
        loadViewHelper.showLoadingDialog(this);
        retrofitUtils.addSubscription(RetrofitUtils.apiStores.getBankCard(), new ApiCallback<BankInfoReponse>() {
            @Override
            public void onSuccess(BankInfoReponse model) {
                if (model.isSuccess()) {
                    if (model.getPayload()!=null&&model.getPayload().size()>0) {
                        tvCommitSelectBank.setVisibility(View.VISIBLE);
                        payload.clear();
                        payload.addAll(model.getPayload());
                        emptyWrapper.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(String msg) {
                loadViewHelper.showErrorResert(SelectBankIDActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         initData();
                    }
                });
            }

            @Override
            public void onFinish() {
                loadViewHelper.closeLoadingDialog();
            }
        });
    }
}
