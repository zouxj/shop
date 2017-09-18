package com.shenyu.laikaword.module.mine;

import com.shenyu.laikaword.module.mine.address.activity.AddAdressActivity;
import com.shenyu.laikaword.module.mine.cards.CardPackageActivity;
import com.shenyu.laikaword.module.mine.cards.fragment.AddBankCardActivity;
import com.shenyu.laikaword.module.mine.systemsetting.UserInfoActivity;

import dagger.Subcomponent;

/**
 * Created by shenyu_zxjCode on 2017/9/13 0013.
 */

@Subcomponent(modules = {MineModule.class})
public interface MineComponent {
    AddAdressActivity inject(AddAdressActivity addAdressActivity);
    UserInfoActivity inject(UserInfoActivity userInfoActivity);
    CardPackageActivity inject(CardPackageActivity carPackActivity);
    PurchaseCardActivity inject(PurchaseCardActivity purchaseCardActivity);
    AddBankCardActivity inject(AddBankCardActivity purchaseCardActivity);
}
