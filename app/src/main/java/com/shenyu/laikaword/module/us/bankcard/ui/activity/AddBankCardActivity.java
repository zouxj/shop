package com.shenyu.laikaword.module.us.bankcard.ui.activity;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.BankModule;
import com.shenyu.laikaword.model.bean.reponse.BankReponse;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.bankcard.presenter.AddBankPresenter;
import com.shenyu.laikaword.module.us.bankcard.ui.fragment.AddBankOneFragment;
import com.shenyu.laikaword.module.us.bankcard.ui.fragment.AddBankTwoFragment;
import com.shenyu.laikaword.module.us.bankcard.view.AddBankView;
import javax.inject.Inject;

import butterknife.OnClick;

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends LKWordBaseActivity implements AddBankView {
    FragmentTransaction fragmentTransaction;
    @Inject
    AddBankTwoFragment addBankTwoFragment;
    @Inject
    AddBankOneFragment addBankOneFragment;
    @Inject
    AddBankPresenter addBankPresenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    public void initView() {
        setToolBarTitle("添加银行卡");
    }

    @Override
    public void doBusiness(Context context) {

        addBankPresenter.subscribeEvent();
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if (addBankOneFragment.isAdded()) {
            fragmentTransaction.show(addBankOneFragment);
        } else {
            fragmentTransaction.add(R.id.fl_bank_frame, addBankOneFragment);
        }
        fragmentTransaction.commit();

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new BankModule(this)).inject(this);
    }



    @OnClick(R.id.rl_toolbar_left)
    public void onViewClicked() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }else{
            getSupportFragmentManager().popBackStack();
        }
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
            case EventType.ACTION_ADDBANK:
                //添加MianFragment
                //添加LeftFragment
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                addBankTwoFragment.setBankInfo((BankReponse) myEvent.object);
                if (addBankTwoFragment.isAdded()) {
                    fragmentTransaction.show(addBankTwoFragment);
                } else {
                    fragmentTransaction.add(R.id.fl_bank_frame, addBankTwoFragment);
                    fragmentTransaction.addToBackStack("tag01");
                }
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addBankPresenter.detachView();
    }
}