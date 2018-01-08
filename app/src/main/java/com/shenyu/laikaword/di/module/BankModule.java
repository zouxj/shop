package com.shenyu.laikaword.di.module;

import android.app.Activity;

import com.shenyu.laikaword.module.us.bankcard.presenter.AddBankPresenter;
import com.shenyu.laikaword.module.us.bankcard.presenter.BankInfoPresenter;
import com.shenyu.laikaword.module.us.bankcard.presenter.SelectBankPresent;
import com.shenyu.laikaword.module.us.bankcard.ui.fragment.AddBankOneFragment;
import com.shenyu.laikaword.module.us.bankcard.ui.fragment.AddBankTwoFragment;
import com.shenyu.laikaword.module.us.bankcard.view.AddBankView;
import com.shenyu.laikaword.module.us.bankcard.view.BankInfoView;
import com.shenyu.laikaword.module.us.bankcard.view.SelectBankView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shenyu_zxjCode on 2017/12/29 0029.
 */

@Module
public class BankModule {
    private Activity activity;
    private SelectBankView selectBankView;
    private AddBankView addBankView;
    private BankInfoView bankInfoView;

    public BankModule(Activity activity, SelectBankView selectBankView ){
        this.selectBankView = selectBankView;
        this.activity=activity;
    }
    public BankModule(AddBankView addBankView){
        this.addBankView=addBankView;
    }
    public BankModule(BankInfoView bankInfoView){
        this.bankInfoView=bankInfoView;
    }
    @Provides
    SelectBankPresent provideSelectBankPresent() {
        return new SelectBankPresent(selectBankView);
    }

    @Provides
    AddBankPresenter provideAddBankPresenter() {
        return new AddBankPresenter(addBankView);

    }
    @Provides
    BankInfoPresenter provideBankInfoPresenter() {
        return new BankInfoPresenter(bankInfoView);
    }
    @Provides
    AddBankOneFragment provideAddBankOneFragment(){
        return new AddBankOneFragment();
    }
    @Provides
    AddBankTwoFragment provideAddBankTwoFragment(){
        return new AddBankTwoFragment();
    }
    @Provides
    BankInfoView provideBankInfoView() {
        return bankInfoView;
    }
    @Provides
    AddBankView provideAddBankView() {
        return addBankView;
    }
    @Provides
    SelectBankView provideSelectBankView() {
        return selectBankView;
    }
}
