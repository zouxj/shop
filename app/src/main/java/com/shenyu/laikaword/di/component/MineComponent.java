package com.shenyu.laikaword.di.component;

import com.shenyu.laikaword.di.module.MineModule;
import com.shenyu.laikaword.module.us.goodcards.ui.activity.CardPackageActivity;
import com.shenyu.laikaword.module.us.bankcard.ui.activity.AddBankCardActivity;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PurchaseCardActivity;
import com.shenyu.laikaword.module.us.appsetting.UserInfoActivity;

import dagger.Subcomponent;

/**
 * Created by shenyu_zxjCode on 2017/9/13 0013.
 */

@Subcomponent(modules = {MineModule.class})
public interface MineComponent {
    UserInfoActivity inject(UserInfoActivity userInfoActivity);
    CardPackageActivity inject(CardPackageActivity carPackActivity);
    PurchaseCardActivity inject(PurchaseCardActivity purchaseCardActivity);
    AddBankCardActivity inject(AddBankCardActivity purchaseCardActivity);
}
