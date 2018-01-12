package com.shenyu.laikaword.di.component;

import com.shenyu.laikaword.di.module.ShopModule;
import com.shenyu.laikaword.di.module.UserAddressModule;
import com.shenyu.laikaword.module.goods.order.ui.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.module.us.address.ui.activity.AddressInfoActivity;

import dagger.Subcomponent;

/**
 * Created by zxjCo on 2018/1/12.
 * 用户地址
 */
@Subcomponent(modules = {UserAddressModule.class})
public interface UserAddressComponent {
    AddressInfoActivity inject(AddressInfoActivity addressInfoActivity);
}
