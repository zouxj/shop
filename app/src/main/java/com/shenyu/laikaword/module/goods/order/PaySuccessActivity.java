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
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class PaySuccessActivity extends LKWordBaseActivity {
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
        new RxTask().addSubscription(this.bindToLifecycle(),Observable.interval(3000, TimeUnit.MILLISECONDS).take(1), new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                IntentLauncher.with(PaySuccessActivity.this).launchFinishCpresent(CardPackageActivity.class);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void setupActivityComponent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
