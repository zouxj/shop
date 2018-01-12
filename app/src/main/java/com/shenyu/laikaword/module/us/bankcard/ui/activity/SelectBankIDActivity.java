package com.shenyu.laikaword.module.us.bankcard.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.di.module.BankModule;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.BankInfoReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.bankcard.presenter.SelectBankPresent;
import com.shenyu.laikaword.module.us.bankcard.view.SelectBankView;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 银行卡管理
 */
public class SelectBankIDActivity extends LKWordBaseActivity implements SelectBankView   {

    @BindView(R.id.card_cy_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_commit_selector)
    TextView tvCommitSelectBank;
    private static String carID;
    private static String carName;
    private List<BankInfoReponse.PayloadBean> payload=new ArrayList<>();
    EmptyWrapper emptyWrapper;
    @Inject
    SelectBankPresent selectBankPresent;
    @Override
    public int bindLayout() {
        return R.layout.activity_usr_card;
    }
    @Override
    public void initView() {

        setToolBarTitle("我的银行卡");
        setToolBarRight(null,R.mipmap.add_icon);
        mToolbarSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddBank();
            }
        });

        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,1, UIUtil.getColor(R.color.divider)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter commonAdapter=  new CommonAdapter<BankInfoReponse.PayloadBean>(R.layout.item_card_list,payload) {
            int selectedPosition=-5;
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
                ImageUitls.loadImgRound(payload.get(position).getBankLogo(), (ImageView) holder.getView(R.id.iv_bandlog_img),R.mipmap.banklogo);
                checkBox.setChecked(payloadBean.getCardId().equals(carID)&&selectedPosition==position);
               if (checkBox.isChecked()){
                   carID = payloadBean.getCardId();
                   carName=payloadBean.getBankName()+"("+ StringUtil.getBankNumber(payloadBean.getCardNo())+")";
               }
                holder.setText(R.id.tv_card_num,"尾号"+ StringUtil.getBankNumber(payloadBean.getCardNo()));
                holder.setText(R.id.tv_card_bank,payloadBean.getBankName());
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
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.bank_empty));
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
    @Override
    public void doBusiness(Context context) {
        selectBankPresent.requestData(this.bindToLifecycle());

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new BankModule(this,this)).inject(this);
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
    public void eventBus(Event myEvent) {
        switch (myEvent.event) {
            case EventType.ACTION_UPDATA_USER_BANK:
                selectBankPresent.requestData(this.bindToLifecycle());
                break;
        }
    }

    @Override
    public void showData(BaseReponse baseReponse) {
        BankInfoReponse model = (BankInfoReponse) baseReponse;
                if (model.isSuccess()) {
                    if (model.getPayload() != null && model.getPayload().size() > 0) {
                        tvCommitSelectBank.setVisibility(View.VISIBLE);
                        payload.clear();
                        payload.addAll(model.getPayload());
                        emptyWrapper.notifyDataSetChanged();
                    }
                }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        selectBankPresent.detachView();
    }
}
