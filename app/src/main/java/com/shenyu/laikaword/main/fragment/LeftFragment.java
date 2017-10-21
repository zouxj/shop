package com.shenyu.laikaword.main.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.module.login.activity.LoginActivity;
import com.shenyu.laikaword.module.mine.address.activity.AddressInfoActivity;
import com.shenyu.laikaword.module.mine.cards.activity.CardBankInfoActivity;
import com.shenyu.laikaword.module.shop.activity.BuyGoodsActivity;
import com.shenyu.laikaword.module.mine.remaining.PurchaseCardActivity;
import com.shenyu.laikaword.module.mine.cards.activity.CardPackageActivity;
import com.shenyu.laikaword.module.mine.remaining.UserRemainingActivity;
import com.shenyu.laikaword.module.mine.systemsetting.activity.SettingSystemActivity;
import com.shenyu.laikaword.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.rxbus.RxSubscriptions;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class LeftFragment extends IKWordBaseFragment {

    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_head)
    ImageView tvUserHead;
    @BindView(R.id.rc_left_view)
    RecyclerView rcLeftView;
    CommonAdapter commonAdapter;
    public static final int[] leftData={
        R.mipmap.left_money_icon,R.mipmap.left_gouwuchei_icon,
                R.mipmap.left_tihuo_icon,R.mipmap.left_wodekabao_icon,
                R.mipmap.left_yinhangka_icon,R.mipmap.left_dizh_icon,R.mipmap.lfet_setting_icon};


    @Override
    public int bindLayout() {
        return R.layout.fragment_left;
    }

  @OnClick({R.id.ly_user_head})
  public void onClick(View view){
        switch (view.getId()){
            case R.id.ly_user_head:
                IntentLauncher.with(getActivity()).launch(LoginActivity.class);
                break;
        }

  }
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @SuppressLint("NewApi")
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_UPDATA_USER:
                                LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
                                if (null!=loginReponse) {
                                    Picasso.with(UIUtil.getContext()).load(loginReponse.getPayload().getAvatar()).placeholder(R.mipmap.left_user_icon)
                                            .error(R.mipmap.left_user_icon).resize(50, 50).transform(new CircleTransform()).into(tvUserHead);
                                    tvUserName.setText(loginReponse.getPayload().getNickname());
                                }else{
                                    tvUserName.setText("未登录");
                                    tvUserHead.setImageBitmap(null);
                                    tvUserHead.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
                                }
                                break;
                        }
                        LogUtil.e(TAG, myEvent.event+"____"+"threadType=>"+Thread.currentThread());
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
    public void doBusiness() {
        List<String> dataList = new ArrayList<>();
        dataList.add("我的余额");
        dataList.add("我的购买");
        dataList.add("我的提货");
        dataList.add("我的卡包");
        dataList.add("银行卡");
        dataList.add("我的地址");
        dataList.add("系统设置");
        subscribeEvent();
        rcLeftView.setLayoutManager(new LinearLayoutManager(getActivity()));
         commonAdapter=  new CommonAdapter<String>(R.layout.item_left_frame,dataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_left_menu,s);
                holder.setBackground(R.id.img_left_menu, UIUtil.getDrawable(leftData[position]));
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                if (position==getItemCount()-1){
                    holder.getView(R.id.ly_line).setVisibility(View.VISIBLE);
                }
                holder.setOnClickListener(R.id.tv_left_fragment, new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
                        if (null==loginReponse) {
                            IntentLauncher.with(getActivity()).launch(LoginActivity.class);
                            return;
                        }
                        switch (position){
                            case 0:
                                //TODO 我的余额
                                IntentLauncher.with(getActivity()).launch(UserRemainingActivity.class);
                                break;
                            case 1:
                                //TODO 我的购买
                                IntentLauncher.with(getActivity()).launch(BuyGoodsActivity.class);
                                break;
                            case 2:
                                //TODO 我的提货
                                IntentLauncher.with(getActivity()).launch(PurchaseCardActivity.class);
                                break;
                            case 3:
                                //TODO 我的卡包
                                IntentLauncher.with(getActivity()).launch(CardPackageActivity.class);
                                break;
                            case 4:
                                //TODO 我的银行卡
                                IntentLauncher.with(getActivity()).launch(CardBankInfoActivity.class);

                                break;
                            case 5:
                                //TODO 我的地址
                                IntentLauncher.with(getActivity()).launch(AddressInfoActivity.class);
                                break;
                            case 6:
                                //TODO 我的设置
                                IntentLauncher.with(getActivity()).launch(SettingSystemActivity.class);
                                break;
                        }
                    }
                });
            }
        };
        rcLeftView.setAdapter(commonAdapter);

    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {
        LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
        if (null!=loginReponse&&loginReponse.getPayload()!=null){
            Picasso.with(UIUtil.getContext()).load(loginReponse.getPayload().getAvatar()) .placeholder(R.mipmap.left_user_icon)
                    .error(R.mipmap.left_user_icon).transform(new CircleTransform()).into(tvUserHead);
       tvUserName.setText(loginReponse.getPayload().getNickname());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
