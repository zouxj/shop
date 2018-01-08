package com.shenyu.laikaword.di.component;

import com.shenyu.laikaword.di.module.BankModule;
import com.shenyu.laikaword.module.us.bankcard.ui.activity.AddBankCardActivity;
import com.shenyu.laikaword.module.us.bankcard.ui.activity.BankInfoActivity;
import com.shenyu.laikaword.module.us.bankcard.ui.activity.SelectBankIDActivity;

import dagger.Subcomponent;

/**
 * Created by shenyu_zxjCode on 2017/12/29 0029.
 * 银行卡模块commponet
 */
@Subcomponent(modules={BankModule.class})
public interface BankComponet {
    /**
     * 选择银行卡
     * @param selectBankIDActivity
     * @return
     */
    SelectBankIDActivity inject(SelectBankIDActivity selectBankIDActivity);
    /**
     *添加银行卡
     */
    AddBankCardActivity inject(AddBankCardActivity addBankCardActivity);

    /**
     *银行卡详细页
     */
    BankInfoActivity inject(BankInfoActivity bankInfoActivity);
}
