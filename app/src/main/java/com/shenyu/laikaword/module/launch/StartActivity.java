package com.shenyu.laikaword.module.launch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.bean.reponse.StartBannerGuangKReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rx.RxTask;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle2.components.RxActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * 启动页
 */
public class StartActivity extends RxActivity {

    @BindView(R.id.img_banner)
    ImageView imageView;
    @BindView(R.id.wl_tv_tiao)
    TextView textView;
    private StartBannerGuangKReponse.PayloadBean payload;

    public int bindLayout() {
        return R.layout.activity_start;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(bindLayout());
        ButterKnife.bind(this);
        doBusiness(this);
    }

    public void doBusiness(Context context) {

//去掉Activity上面的状态栏
        if (!StringUtil.validText(SPUtil.getString("start_app", ""))) {
            //TODO 第一次登录
            IntentLauncher.with(this).launchFinishCpresent(WelcomePageActivity.class);
        } else {
            RetrofitUtils.getRetrofitUtils().setLifecycleTransformer(this.bindToLifecycle()).addSubscription(RetrofitUtils.apiStores.appStartUp(), new ApiCallback<StartBannerGuangKReponse>() {
                @Override
                public void onSuccess(StartBannerGuangKReponse model) {
                    if (model.isSuccess()&&null!=model.getPayload()&&StringUtil.validText(model.getPayload().getImageUrl())&&StringUtil.validText(model.getPayload().getImageUrl())) {
                        payload = model.getPayload();
                        imageView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        String url = payload.getImageUrl();
                        if (StringUtil.validText(url)) {
                            Picasso.with(UIUtil.getContext()).load(url).placeholder(R.mipmap.start_icon).error(R.mipmap.start_icon).into(imageView);
                        }
                        RxTask.countdown(StartActivity.this.bindToLifecycle(), 3).subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                textView.setText("跳过广告 " + aLong + "秒");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                    }else {
//                        IntentLauncher.with(StartActivity.this).launchFinishCpresent(MainActivity.class);
                    }
                }

                @Override
                public void onFailure(String msg) {

                }

                @Override
                public void onFinish() {

                }
            });

            Observable.interval(4, TimeUnit.SECONDS).take(1).compose(this.<Long>bindToLifecycle()).subscribe(new Observer<Long>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Long aLong) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    IntentLauncher.with(StartActivity.this).launchFinishCpresent(MainActivity.class);
                }
            });
        }
    }


    @OnClick(R.id.wl_tv_tiao)
    public void onViewClicked() {
        IntentLauncher.with(StartActivity.this).launchFinishCpresent(MainActivity.class);
    }
}
