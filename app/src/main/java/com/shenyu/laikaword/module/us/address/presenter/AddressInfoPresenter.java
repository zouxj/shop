package com.shenyu.laikaword.module.us.address.presenter;

import android.app.Dialog;
import android.content.Context;

import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.model.bean.reponse.AddressReponse;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.us.address.ui.activity.AddressInfoActivity;
import com.shenyu.laikaword.module.us.address.view.AddressInfoView;
import com.shenyu.laikaword.module.us.bankcard.view.BankInfoView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zxjCo on 2018/1/12.
 * 用户地址信息
 */

public class AddressInfoPresenter extends BasePresenter<AddressInfoView> {
    public AddressInfoPresenter(AddressInfoView addressInfoView){
        attachView(addressInfoView);
        this.mvpView=addressInfoView;
    }

    @Override
    public void distribute(Event myEvent) {
        mvpView.subscribeEvent(myEvent);
    }
    //设置修改地址
    public void setAddress(LifecycleTransformer lifecycleTransformer,AddressReponse.PayloadBean payloadBean, final int def){
        Map<String,String> mapParam = new HashMap<>();
        mapParam.put("addressId",payloadBean.getAddressId());
        mapParam.put("phone",payloadBean.getPhone());
        mapParam.put("receiveName",payloadBean.getReceiveName());
        mapParam.put("detail",payloadBean.getDetail());
        mapParam.put("default",def+"");
        mapParam.put("province",payloadBean.getProvince());
        mapParam.put("city",payloadBean.getCity());
        mapParam.put("district",payloadBean.getDistrict());
        addSubscription(lifecycleTransformer,apiStores.setAddress(mapParam), new ApiCallback<BaseReponse>() {
            @Override
            public void onSuccess(BaseReponse model) {
                if (model.isSuccess()) {
                    switch (def){
                        case 0:
                            ToastUtil.showToastShort("取消默认设置");
                            break;
                        case 1:
                            ToastUtil.showToastShort("设置默认成功");
                            break;
                    }

                    RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_ADDRESS,null));
                }
            }

            @Override
            public void onFailure(String msg) {
            }

            @Override
            public void onFinish() {

            }

        });

    }

    /**
     * 删除地址
     * @param context
     * @param lifecycleTransformer
     * @param payloadBean
     */
    public void deleteAddress(final Context context, final LifecycleTransformer lifecycleTransformer, final AddressReponse.PayloadBean payloadBean){
        DialogHelper.deleteBankDialog(context,"地址删除后,不可再使用该地址", "确认删除",new DialogHelper.ButtonCallback() {
            @Override
            public void onNegative(Dialog dialog) {
                addSubscription(lifecycleTransformer,apiStores.deleteAddress(payloadBean.getAddressId()), new ApiCallback<BaseReponse>() {
                    @Override
                    public void onSuccess(BaseReponse model) {
                        if (model.isSuccess())
                            RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_ADDRESS, null));

                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
            }

            @Override
            public void onPositive(Dialog dialog) {

            }
        });
    }

    public void getAddressInfo(LifecycleTransformer lifecycleTransformer) {
        addSubscription(lifecycleTransformer,RetrofitUtils.apiStores.getAddress(), new ApiCallback<AddressReponse>() {
            @Override
            public void onSuccess(AddressReponse model) {
                int j=0;
                if (model.isSuccess()){
                    mvpView.getAddressInfo(model);
                    for (AddressReponse.PayloadBean payloadBean:model.getPayload()) {
                        if (payloadBean.getDefaultX()==1) {
                            j=1;
                            SPUtil.saveObject(Constants.SAVA_ADDRESS,payloadBean);
                            break;
                        }
                    }
                    if (j==0){
                        SPUtil.removeSp(Constants.SAVA_ADDRESS);
                    }

                }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
