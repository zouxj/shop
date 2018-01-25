package com.shenyu.laikaword.di.component;

import com.shenyu.laikaword.di.module.mine.MineModule;
import com.shenyu.laikaword.module.us.goodcards.ui.activity.CardPackageActivity;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PurchaseCardActivity;
import com.shenyu.laikaword.module.us.appsetting.UserInfoActivity;
import com.shenyu.laikaword.module.us.resell.ui.activity.ResellInputCodeActivity;
import com.shenyu.laikaword.module.us.resell.ui.activity.ResellActivity;

import dagger.Subcomponent;

/**
 * Created by shenyu_zxjCode on 2017/9/13 0013.
 */

@Subcomponent(modules = {MineModule.class})
public interface MineComponent {
    UserInfoActivity inject(UserInfoActivity userInfoActivity);
    CardPackageActivity inject(CardPackageActivity carPackActivity);
    PurchaseCardActivity inject(PurchaseCardActivity purchaseCardActivity);
    ResellActivity inject(ResellActivity zhuanMaiActivity);
    //转卖输入
    ResellInputCodeActivity inject(ResellInputCodeActivity resellInputCodeActivity);
}
