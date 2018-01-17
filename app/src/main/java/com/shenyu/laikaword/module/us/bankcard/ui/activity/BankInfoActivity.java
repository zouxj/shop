package com.shenyu.laikaword.module.us.bankcard.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.di.module.BankModule;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.ReslerAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.BankInfoReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.bankcard.presenter.BankInfoPresenter;
import com.shenyu.laikaword.module.us.bankcard.view.BankInfoView;
import com.shenyu.laikaword.ui.view.widget.DeleteRecyclerView;
import com.shenyu.laikaword.ui.view.widget.SwipeItemLayout;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import com.shenyu.laikaword.helper.DialogHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class BankInfoActivity extends LKWordBaseActivity implements  BankInfoView {

    @BindView(R.id.card_cy_list)
    RecyclerView recyclerView;
    ReslerAdapter reslerAdapter ;
    EmptyWrapper emptyWrapper;
    @Inject
    BankInfoPresenter bankInfoPresenter;
    private List<BankInfoReponse.PayloadBean> payload=new ArrayList<>();
    @Override
    public int bindLayout() {
        return R.layout.activity_card_bank_info;
    }

    @Override
    public void initView() {
        setToolBarTitle("银行卡");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(1),UIUtil.getColor(R.color.main_bg_gray)));
        reslerAdapter = new ReslerAdapter(R.layout.item_cardinfo_list,payload){
            @Override
            protected void convert(ViewHolder viewHolder, BankInfoReponse.PayloadBean payloadBean, final int position) {
                viewHolder.setText(R.id.tv_bank_no,"尾号"+ StringUtil.getBankNumber(payloadBean.getCardNo()));
                viewHolder.setText(R.id.tv_card_bank,payloadBean.getBankName());
                ImageUitls.loadImgRound(payloadBean.getBankLogo(),
                        (ImageView) viewHolder.getView(R.id.iv_bank_log),R.mipmap.banklogo);
                viewHolder.setOnClickListener(R.id.item_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                DialogHelper.deleteBankDialog(BankInfoActivity.this, "解除绑定后银行卡服务将不可使用,包括快捷支付","解除绑定",new DialogHelper.ButtonCallback() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        bankInfoPresenter.deleteBank(BankInfoActivity.this.bindToLifecycle(),payload.get(position).getCardId());
                }

                    @Override
                    public void onPositive(Dialog dialog) {

                    }
                });
                    }
                });
            }
        };
        emptyWrapper = new EmptyWrapper(reslerAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.bank_empty));
        recyclerView.setAdapter(emptyWrapper);

//        recyclerView.setOnItemClickListener(new DeleteRecyclerView.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//            }
//
//            @Override
//            public void onDeleteClick(final int position) {
//                DialogHelper.deleteBankDialog(BankInfoActivity.this, "解除绑定后银行卡服务将不可使用,包括快捷支付","解除绑定",new DialogHelper.ButtonCallback() {
//                    @Override
//                    public void onNegative(Dialog dialog) {
//                        bankInfoPresenter.deleteBank(BankInfoActivity.this.bindToLifecycle(),payload.get(position).getCardId());
//                }
//
//                    @Override
//                    public void onPositive(Dialog dialog) {
//
//                    }
//                });
//
//            }
//
//        });
    }

    @Override
    public void doBusiness(Context context) {
        bankInfoPresenter.loadData(this.bindToLifecycle());
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
        LaiKaApplication.get(this).getAppComponent().plus(new BankModule(this)).inject(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bankInfoPresenter.detachView();
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void loadSucceed(BaseReponse baseReponse) {

    }


    @Override
    public void loadFailure() {

    }

    @Override
    public void subscribeEvent(Event eventType) {
        switch (eventType.event) {
            case EventType.ACTION_UPDATA_USER_BANK:
                bankInfoPresenter.loadData(this.bindToLifecycle());
                break;
        }
    }

    @Override
    public void showData(BaseReponse baseReponse) {
        BankInfoReponse model= (BankInfoReponse) baseReponse;
        if (model.isSuccess()) {
            payload.clear();
            payload.addAll(model.getPayload());
            if (payload.size()<=0){
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
            }else {
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
            emptyWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void deteBank(boolean bool) {
        if (bool) {
            ToastUtil.showToastShort("删除成功！");
            RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
            bankInfoPresenter.loadData(this.bindToLifecycle());
        }else {
            ToastUtil.showToastShort("删除失败！");
        }
    }

}
