package com.shenyu.laikaword.module.home.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.module.login.ui.activity.LoginActivity;
import com.shenyu.laikaword.module.us.address.ui.activity.AddressInfoActivity;
import com.shenyu.laikaword.module.us.bankcard.ui.activity.CardBankInfoActivity;
import com.shenyu.laikaword.module.us.appsetting.UserInfoActivity;
import com.shenyu.laikaword.module.goods.BuyGoodsActivity;
import com.shenyu.laikaword.module.us.wallet.remaining.PurchaseCardActivity;
import com.shenyu.laikaword.module.us.goodcards.ui.activity.CardPackageActivity;
import com.shenyu.laikaword.module.us.wallet.remaining.UserRemainingActivity;
import com.shenyu.laikaword.module.us.appsetting.SettingSystemActivity;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;


public class LeftFragment extends IKWordBaseFragment {

    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_head)
    ImageView tvUserHead;
    @BindView(R.id.rc_left_view)
    RecyclerView rcLeftView;
    List<ShopMainReponse.EntranceListBean> dataList = new ArrayList<>();
    CommonAdapter<ShopMainReponse.EntranceListBean> commonAdapter;
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
                LoginReponse loginReponse = Constants.getLoginReponse();
                if (null!=loginReponse) {
                    IntentLauncher.with(getActivity()).launch(UserInfoActivity.class);
                }else{
                    IntentLauncher.with(getActivity()).launch(LoginActivity.class);
                }

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
                                LoginReponse loginReponse = Constants.getLoginReponse();
                                if (null!=loginReponse) {
                                    ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(),tvUserHead);
                                    tvUserName.setText(loginReponse.getPayload().getNickname());
                                }else{
                                    tvUserName.setText("未登录");
                                    tvUserHead.setImageBitmap(null);
                                    tvUserHead.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
                                }
                                break;
                            case EventType.ACTION_MAIN_SETDATE:
                                initLeftData();
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
        subscribeEvent();

        rcLeftView.setLayoutManager(new LinearLayoutManager(getActivity()));
         commonAdapter=  new CommonAdapter<ShopMainReponse.EntranceListBean>(R.layout.item_left_frame,dataList) {
            @SuppressLint("NewApi")
            @Override
            protected void convert(ViewHolder holder, final ShopMainReponse.EntranceListBean entranceListBean, final int position) {
                holder.setText(R.id.tv_left_menu,entranceListBean.getTitle());
                ImageView imageView=   (ImageView) holder.getView(R.id.img_left_menu);
                imageView.setImageBitmap(null);
                if (entranceListBean.getImgUrl()!=0) {
                    imageView.setBackground(UIUtil.getDrawable(entranceListBean.getImgUrl()));
                }
                if (StringUtil.validText(entranceListBean.getIconURL())) {
                    ImageUitls.loadImg(entranceListBean.getIconURL(), imageView);
                }
//                if (position==dataList.size()-1){
//                    holder.getView(R.id.ly_line).setVisibility(View.VISIBLE);
//                }
                holder.itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
                        if (null==loginReponse) {
                            IntentLauncher.with(getActivity()).launch(LoginActivity.class);
                            return;
                        }

                        if (entranceListBean.getTitle().equals("系统设置")){
                                //TODO 我的设置
                                IntentLauncher.with(getActivity()).launch(SettingSystemActivity.class);
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
                                default:
                                    IntentLauncher.with(getActivity()).launchViews(entranceListBean.getUrl());
                                    break;

                        }
                    }
                });
            }

//            @Override
//            public void onBindViewHolder(ViewHolder holder, final int position) {
//                super.onBindViewHolder(holder, position);
//                if (position==getItemCount()-1){
//                    holder.getView(R.id.ly_line).setVisibility(View.VISIBLE);
//                }
//
//            }
        };
        rcLeftView.setAdapter(commonAdapter);
        initLeftData();

    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {
        LoginReponse loginReponse = Constants.getLoginReponse();
        if (null!=loginReponse&&loginReponse.getPayload()!=null){
            ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(),tvUserHead);
       tvUserName.setText(loginReponse.getPayload().getNickname());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void  initLeftData(){
        dataList.clear();
        dataList.add(new ShopMainReponse.EntranceListBean("我的余额",leftData[0],null,null,false));
        dataList.add(new ShopMainReponse.EntranceListBean("我的购买",leftData[1],null,null,false));
        dataList.add(new ShopMainReponse.EntranceListBean("我的提货",leftData[2],null,null,false));
        dataList.add(new ShopMainReponse.EntranceListBean("我的卡包",leftData[3],null,null,false));
        dataList.add(new ShopMainReponse.EntranceListBean("银行卡",leftData[4],null,null,false));
        dataList.add(new ShopMainReponse.EntranceListBean("我的地址",leftData[5],null,null,false));
        ShopMainReponse shopMainReponse= (ShopMainReponse) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
        if (shopMainReponse!=null){
            List<ShopMainReponse.EntranceListBean> entranceList= shopMainReponse.getPayload().getEntranceList();
            for (ShopMainReponse.EntranceListBean listBean:entranceList) {
                dataList.add(listBean);
            }
        }
        dataList.add(new ShopMainReponse.EntranceListBean("系统设置",leftData[6],null,null,false));
        commonAdapter.notifyDataSetChanged();
    }
}
