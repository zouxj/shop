package com.shenyu.laikaword.module.us.address.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.Interactor.IOptionPickerVierCallBack;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.model.bean.reponse.AddressReponse;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.us.address.view.AddressInfoView;
import com.shenyu.laikaword.module.us.address.view.EditAddressView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func4;

public class EditAddressPresenter extends BasePresenter<EditAddressView> {

    public EditAddressPresenter(EditAddressView editAddressView){
        attachView(editAddressView);
        this.mvpView=editAddressView;
    }

    public void setAddress(LifecycleTransformer lifecycleTransformer,Map<String, String> mapParam){
        addSubscription(lifecycleTransformer,apiStores.setAddress(mapParam), new ApiCallback<BaseReponse>() {
            @Override
            public void onSuccess(BaseReponse model) {
                mvpView.loadSucceed(model);

            }

            @Override
            public void onFailure(String msg) {
                mvpView.loadFailure();

            }

            @Override
            public void onFinish() {

            }


        });
    }

}
