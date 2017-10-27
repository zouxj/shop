package com.shenyu.laikaword.module.launch;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.BaseReponse;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.bean.reponse.StartBannerGuangKReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rx.RxTask;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 启动页
 */
public class StartActivity extends LKWordBaseActivity {

    @BindView(R.id.img_banner)
    ImageView imageView;
    @BindView(R.id.wl_tv_tiao)
    TextView textView;
    private StartBannerGuangKReponse.PayloadBean payload;
    @Override
    public int bindLayout() {
        return R.layout.activity_start;
    }
    String usename;
    String password;
    @Override
    public void doBusiness(Context context) {
        usename = SPUtil.getString("usename", "");
        password = SPUtil.getString("password", "");
        if (!StringUtil.validText(SPUtil.getString("start_app", ""))) {
            //TODO 第一次登录
            IntentLauncher.with(this).launch(WelcomePageActivity.class);
            SPUtil.putString("start_app", "one");
            finish();
        } else {
            retrofitUtils.addSubscription(RetrofitUtils.apiStores.appStartUp(), new ApiCallback<StartBannerGuangKReponse>() {
                @Override
                public void onSuccess(StartBannerGuangKReponse model) {
                    if (model.isSuccess()) {
                        payload = model.getPayload();
                        imageView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        ImageUitls.loadImg(payload.getImageUrl(), imageView);
                        RxTask.countdown(3).doOnUnsubscribe(new Action0() {
                            @Override
                            public void call() {

                            }
                        }).subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                IntentLauncher.with(StartActivity.this).launch(MainActivity.class);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                textView.setText(integer + "秒");
                            }
                        });

                    } else {
                        rx.Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                IntentLauncher.with(StartActivity.this).launch(MainActivity.class);
                                finish();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(String msg) {
                    rx.Observable.timer(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            IntentLauncher.with(StartActivity.this).launch(MainActivity.class);
                            finish();
                        }
                    });
                }

                @Override
                public void onFinish() {

                }
            });
        }
    }

    @Override
    public void setupActivityComponent() {

    }

}
