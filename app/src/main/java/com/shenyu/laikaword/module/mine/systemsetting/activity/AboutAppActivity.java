package com.shenyu.laikaword.module.mine.systemsetting.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.reponse.CheckAppUpdateReponse;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.http.downloadmanager.DownLoadService;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.zxj.utilslibrary.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutAppActivity extends LKWordBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_about_app;
    }

    @Override
    public void initView() {
        setToolBarTitle("关于我们");
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_check_update, R.id.tv_get_pignfen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_check_update:
                RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.checkUpdate(1), new ApiCallback<CheckAppUpdateReponse>() {
                    @Override
                    public void onSuccess(final CheckAppUpdateReponse model) {
                        if (model.isSuccess()){
                               DialogHelper.makeUpdate(AboutAppActivity.this, "发现新版本", model.getPayload().getMessage(), "取消", "更新", model.getPayload().getType().equals("2"), new DialogHelper.ButtonCallback() {
                                   @Override
                                   public void onNegative(Dialog dialog) {
                                        //TODO 去更新
                                       Intent intent = new Intent(AboutAppActivity.this, DownLoadService.class);
                                        intent.putExtra("DWONAPKURL",model.getPayload().getDownloadUrl());
                                       startService(intent);

                                   }

                                   @Override
                                   public void onPositive(Dialog dialog) {

                                   }
                               }).show();
                    }
                    else {
                            ToastUtil.showToastLong("已经是最新版本");
                        }
                    }
                    @Override
                    public void onFailure(String msg) {

                    }
                    @Override
                    public void onFinish() {

                    }
                });
                break;
            case R.id.tv_get_pignfen:
                break;
        }
    }
}
