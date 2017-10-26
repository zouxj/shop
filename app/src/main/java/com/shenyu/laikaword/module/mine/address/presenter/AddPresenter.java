package com.shenyu.laikaword.module.mine.address.presenter;

import com.shenyu.laikaword.module.mine.address.view.AddressView;

/**
 * Created by shenyu_zxjCode on 2017/9/13 0013.
 */

public class AddPresenter {
    private AddressView addressView;
    public AddPresenter(AddressView addressView) {
        this.addressView=addressView;
    }

    /**
     * 添加地址
     */
    public void requestData(){
        //TODO 添加地址借口
        addressView.loadFinished();

    }
}