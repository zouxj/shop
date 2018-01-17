package com.shenyu.laikaword.di.module;

import android.support.v7.widget.LinearLayoutManager;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.module.goods.order.view.ConfirmOrderView;
import com.shenyu.laikaword.module.us.address.presenter.AddressInfoPresenter;
import com.shenyu.laikaword.module.us.address.presenter.EditAddressPresenter;
import com.shenyu.laikaword.module.us.address.presenter.SelectAddressPresneter;
import com.shenyu.laikaword.module.us.address.view.AddressInfoView;
import com.shenyu.laikaword.module.us.address.view.EditAddressView;
import com.shenyu.laikaword.module.us.address.view.SelectAddressView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zxjCo on 2018/1/12.
 * 用户地址
 */
@Module
public class UserAddressModule {
    private BaseLoadView baseLoadView;
    public UserAddressModule(BaseLoadView baseLoadView){
        this.baseLoadView=baseLoadView;
    }

    @Provides
    public AddressInfoPresenter provideAddressInfoPresenter(){
        return new AddressInfoPresenter((AddressInfoView) baseLoadView);
    }

    @Provides
    public EditAddressPresenter provideEditAddressPresenter(){
        return new EditAddressPresenter((EditAddressView) baseLoadView);
    }
    @Provides
    public SelectAddressPresneter provideSelectAddressPresneter(){
        return new SelectAddressPresneter((SelectAddressView) baseLoadView);
    }
}
