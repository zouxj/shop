package com.shenyu.laikaword.module.us.appsetting.updateus;

import android.content.Context;
import android.widget.EditText;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.base.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改昵称
 */
public class UpdateUserNameActivity extends LKWordBaseActivity {


    @BindView(R.id.update_et_uer_name)
    EditText updateEtUerName;

    @Override
    public int bindLayout() {
        return R.layout.activity_update_user_name;
    }

    @Override
    public void initView() {
        setToolBarTitle("昵称");
        setToolBarRight("完成", 0);
    }
    String headURL;
    @Override
    public void doBusiness(Context context) {
        LoginReponse loginReponse = Constants.getLoginReponse();
        if (null != loginReponse){
            headURL=loginReponse.getPayload().getAvatar();
        updateEtUerName.setText(loginReponse.getPayload().getNickname());
    }

    }

    @Override
    public void setupActivityComponent() {

    }


    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        if (StringUtil.validText(updateEtUerName.getText().toString().trim())) {
            RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.editInfo(updateEtUerName.getText().toString().trim(), headURL), new ApiCallback<BaseReponse>() {
                @Override
                public void onSuccess(BaseReponse model) {
                    if (model.isSuccess()) {
                        ToastUtil.showToastShort("修改成功");
                        RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                        finish();
                    } else {
                        ToastUtil.showToastShort("修改失败");
                    }
                }

                @Override
                public void onFailure(String msg) {

                }

                @Override
                public void onFinish() {

                }
            });
        }else {
            ToastUtil.showToastShort("请输入昵称");
        }

//        RetrofitUtils.getRetrofitUtils().apiStores.editInfo(updateEtUerName.getText().toString().trim(), headURL).flatMap(new Func1<BaseReponse, Observable<LoginReponse>>() {
//            @Override
//            public Observable<LoginReponse> call(BaseReponse baseReponse) {
//                if (baseReponse.isSuccess()){
//                    return  RetrofitUtils.getRetrofitUtils().apiStores.getUserInfo();
//                }
//                return null;
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LoginReponse>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(LoginReponse loginReponse) {
//                if (loginReponse.isSuccess()){
//                    SPUtil.saveObject(Constants.LOGININFO_KEY,loginReponse);
//                    ToastUtil.showToastShort("修改成功");
//                    RxBus.getDefault().post(new EventType(EventType.ACTION_UPDATA_USER,null));
//                    finish();
//                }else{
//                    ToastUtil.showToastShort(loginReponse.getError().getMessage());
//                }
//            }
//        });
    }
}
