package com.shenyu.laikaword.module.goods.order;

import android.content.Context;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.shenyu.laikaword.module.us.goodcards.ui.activity.CardPackageActivity;
import com.shenyu.laikaword.model.rxjava.rx.RxTask;
import com.zxj.utilslibrary.utils.IntentLauncher;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

public class PaySuccessActivity extends LKWordBaseActivity {
    private RxTask rxTask;
    @Override
    public int bindLayout() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initView() {
        setToolBarTitle("支付完成");

    }

    @OnClick(R.id.tv_back_main)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_back_main:
                IntentLauncher.with(PaySuccessActivity.this).launchFinishCpresent(MainActivity.class);
                break;
        }
    }



    @Override
    public void doBusiness(Context context) {
         rxTask = new RxTask();
        rxTask.addSubscription(Observable.interval(3000, TimeUnit.MILLISECONDS).take(1), new Subscriber() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(Object o) {
                IntentLauncher.with(PaySuccessActivity.this).launchFinishCpresent(CardPackageActivity.class);
            }
        });
    }

    @Override
    public void setupActivityComponent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxTask.onUnsubscribe();
    }
}
