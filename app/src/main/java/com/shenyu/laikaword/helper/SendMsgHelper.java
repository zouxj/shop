package com.shenyu.laikaword.helper;

import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by shenyu_zxjCode on 2017/9/18 0018.
 * 短信发送帮助类
 */

public final class SendMsgHelper {
    /**
     * 发送短信验证码
     * @param mSend
     */
    public static  void sendMsg( LifecycleTransformer lifecycleTransforme,final TextView mSend, final String phone, final String codeTpe){
        if (!StringUtil.validText(phone)){
            ToastUtil.showToastShort("请输入手机号");
            return;
        }
        if (phone.length()<11){
            ToastUtil.showToastShort("请输入有效手机号");
            return;
        }
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.getSMCode(phone, codeTpe), new ApiCallback<BaseReponse>() {
            @Override
            public void onSuccess(BaseReponse model) {
                if (model.isSuccess())
                    countdownTime(mSend);

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.showToastShort(msg);
            }

            @Override
            public void onFinish() {

            }
        });

    }

    /**
     * 倒计时
     * @param mSend
     */
    private static void countdownTime(final TextView mSend) {
        final int count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count+1) //设置循环11次
                .map(new Function<Long, Long>() {

                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count-aLong; //
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mSend.setEnabled(false);//在发送数据的时候设置为不能点击
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.d("onNext: "+aLong);
                        mSend.setText(aLong+"秒");
                        mSend.setTextColor(UIUtil.getColor(R.color.gray));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d("onCompleted: ");
                        mSend.setEnabled(true);
                        mSend.setText("获取验证码");//数据发送完后设置为原来的文字
                        mSend.setTextColor(UIUtil.getColor(R.color.app_theme_red));
                    }
                });
    }
}
