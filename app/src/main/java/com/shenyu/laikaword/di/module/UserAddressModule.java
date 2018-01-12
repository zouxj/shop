package com.shenyu.laikaword.di.module;

import android.support.v7.widget.LinearLayoutManager;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.module.goods.order.view.ConfirmOrderView;
import com.shenyu.laikaword.module.us.address.presenter.AddressInfoPresenter;
import com.shenyu.laikaword.module.us.address.view.AddressInfoView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zxjCo on 2018/1/12.
 * 用户地址
 */
@Module
public class UserAddressModule {
    private AddressInfoView mAddressInfoView;
    public UserAddressModule(AddressInfoView addressInfoView){
        this.mAddressInfoView=addressInfoView;
    }

    @Provides
    public AddressInfoPresenter provideAddressInfoPresenter(){
        return new AddressInfoPresenter(mAddressInfoView);
    }
}
