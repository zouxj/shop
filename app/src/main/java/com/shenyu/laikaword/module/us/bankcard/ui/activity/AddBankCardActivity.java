package com.shenyu.laikaword.module.us.bankcard.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.MineModule;
import com.shenyu.laikaword.model.bean.reponse.BankReponse;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.bankcard.ui.fragment.AddBankOneFragment;
import com.shenyu.laikaword.module.us.bankcard.ui.fragment.AddBankTwoFragment;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends LKWordBaseActivity {
    @Inject
    FragmentTransaction fragmentTransaction;
    @Inject
    AddBankTwoFragment addBankTwoFragment;
    @Inject
    AddBankOneFragment addBankOneFragment;
    @Override
    public int bindLayout() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    public void doBusiness(Context context) {
        setToolBarTitle("添加银行卡");
        //添加MianFragment
        fragmentTransaction.add(R.id.fl_bank_frame, addBankOneFragment);
        //添加LeftFragment
        fragmentTransaction.commit();
        subscribeEvent();

    }

    //
    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(getSupportFragmentManager())).inject(this);
    }

    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_ADDBANK:
                                //添加MianFragment
                                //添加LeftFragment
                                addBankTwoFragment.setBankInfo((BankReponse) myEvent.object);
                                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.hide(addBankOneFragment);
                                fragmentTransaction.add(R.id.fl_bank_frame, addBankTwoFragment);
                                fragmentTransaction.addToBackStack("tag");
                                fragmentTransaction.commit();
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


    @OnClick(R.id.rl_toolbar_left)
    public void onViewClicked() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }else{
            getSupportFragmentManager().popBackStack();
        }
    }
}