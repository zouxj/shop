package com.shenyu.laikaword.di.component;

import com.shenyu.laikaword.di.module.ShopModule;
import com.shenyu.laikaword.module.goods.order.ui.activity.ConfirmOrderActivity;

import dagger.Subcomponent;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */
@Subcomponent(modules = {ShopModule.class})
public interface ShopCommponent {
    ConfirmOrderActivity inject(ConfirmOrderActivity confirmOrderActivity);

}
