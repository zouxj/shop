package com.shenyu.laikaword.helper;

import android.graphics.Color;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by shenyu_zxjCode on 2017/9/18 0018.
 * 短信发送帮助类
 */

public class SendMsgHelper {
    /**
     * 发送短信验证码
     * @param mSend
     */
    public static  void sendMsg(final TextView mSend, final String phone){
        final int count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count+1) //设置循环11次
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return count-aLong; //
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //TODO 发送请求
                        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.getSMCode(phone, "phoneLogin"), new ApiCallback<BaseReponse>() {
                            @Override
                            public void onSuccess(BaseReponse model) {
                                if (model.isSuccess()){
                                    ToastUtil.showToastShort("短信发送成功");
                                }else {
                                    ToastUtil.showToastShort("短信发送失败");
                                }
                            }

                            @Override
                            public void onFailure(String msg) {

                            }

                            @Override
                            public void onFinish() {

                            }
                        });
                        mSend.setEnabled(false);//在发送数据的时候设置为不能点击
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d("onCompleted: ");
                        mSend.setEnabled(true);
                        mSend.setText("获取验证码");//数据发送完后设置为原来的文字
                        mSend.setTextColor(UIUtil.getColor(R.color.app_theme_red));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) { //接受到一条就是会操作一次UI
                        LogUtil.d("onNext: "+aLong);
                        mSend.setText(aLong+"秒");
                        mSend.setTextColor(UIUtil.getColor(R.color.gray));
                    }
                });
    }
}
