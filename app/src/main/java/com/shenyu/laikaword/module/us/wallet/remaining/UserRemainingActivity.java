package com.shenyu.laikaword.module.us.wallet.remaining;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.us.wallet.DetailMoneyActivity;
import com.shenyu.laikaword.module.us.wallet.recharge.RechargeMoneyActivity;
import com.shenyu.laikaword.module.us.wallet.withdraw.WthdrawMoneyActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 我的余额
 */
public class UserRemainingActivity extends LKWordBaseActivity {


    @BindView(R.id.re_tv_sum)
    TextView reTvSum;

    @Override
    public int bindLayout() {
        return R.layout.activity_user_remaining;
    }

    @Override
    public void doBusiness(Context context) {
      LoginReponse loginReponse = Constants.getLoginReponse();
      if (null!=loginReponse&& StringUtil.validText(loginReponse.getPayload().getMoney()))
          reTvSum.setText(loginReponse.getPayload().getMoney());

        subscribeEvent();
    }

    @Override
    public void initView() {
        setToolBarTitle("我的余额");
        setToolBarRight("明细",0);

    }

    @Override
    public void setupActivityComponent() {

    }
    @OnClick({R.id.re_bt_pay,R.id.re_bt_withdraw,R.id.rl_toolbar_subtitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_bt_pay:
                //TODO 充值
                IntentLauncher.with(this).launch(RechargeMoneyActivity.class);
                break;
            case R.id.re_bt_withdraw:
                //TODO 提现
                IntentLauncher.with(this).put("acountyue", reTvSum.getText().toString().trim()).launch(WthdrawMoneyActivity.class);
                break;
            case R.id.rl_toolbar_subtitle:
                //TODO 明细
                IntentLauncher.with(this).launch(DetailMoneyActivity.class);
                break;
        }
    }
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_UPDATA_USER:
                                LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
                                if (null!=loginReponse&&loginReponse.getPayload()!=null) {
                                    reTvSum.setText(loginReponse.getPayload().getMoney());
                                }else {
                                    reTvSum.setText("");
                                }
                                break;
                        }
//            }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e(TAG, "onError");
                        /**
                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
                         */
                        subscribeEvent();
                    }
                });
        RxSubscriptions.add(mRxSub);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mRxSub);
    }
}
