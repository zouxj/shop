package com.shenyu.laikaword.module.shop;

import com.shenyu.laikaword.module.shop.activity.ConfirmOrderActivity;

import dagger.Subcomponent;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */
@Subcomponent(modules = {ShopModule.class})
public interface ShopCommponent {
    ConfirmOrderActivity inject(ConfirmOrderActivity confirmOrderActivity);
}
