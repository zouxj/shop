package com.shenyu.laikaword.di.component;

import android.content.Context;
import android.view.View;

import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.ShopModule;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.shenyu.laikaword.module.goods.order.ui.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PurchaseCardActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

import butterknife.OnClick;
import dagger.Subcomponent;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */
@Subcomponent(modules = {ShopModule.class})
public interface ShopCommponent {
    ConfirmOrderActivity inject(ConfirmOrderActivity confirmOrderActivity);

}
