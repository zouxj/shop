package com.shenyu.laikaword.module.home.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.helper.StatusBarManager;
import com.shenyu.laikaword.model.adapter.MultiItemTypeAdapter;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.model.adapter.itemviewdelegeate.HomeLeftItemViewDelegate;
import com.shenyu.laikaword.model.adapter.itemviewdelegeate.HomeLeftLastItemViewDelegate;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.module.login.ui.activity.LoginActivity;
import com.shenyu.laikaword.module.us.appsetting.UserInfoActivity;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.zxj.utilslibrary.utils.DeviceInfo;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
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
    @BindView(R.id.ly_user_head)
            LinearLayout leftLayout;
    List<ShopMainReponse.EntranceListBean> dataList = new ArrayList<>();
    MultiItemTypeAdapter<ShopMainReponse.EntranceListBean> commonAdapter;
    public static final int[] leftData={
        R.mipmap.left_money_icon,R.mipmap.left_gouwuchei_icon,
                R.mipmap.left_tihuo_icon,R.mipmap.left_wodekabao_icon,
                R.mipmap.left_yinhangka_icon,R.mipmap.left_dizh_icon,R.mipmap.lfet_setting_icon};


    @Override
    public int bindLayout() {
        return R.layout.fragment_left;
    }

    @Override
    public void initView(View view) {
        StatusBarManager statusBarManager = new StatusBarManager(getActivity(),UIUtil.getColor(R.color.app_theme_red));
        int statusBarHeight=  statusBarManager.getStatusBarHeight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) leftLayout.getLayoutParams();
            params.topMargin=statusBarHeight;
            leftLayout.setLayoutParams(params);
        }
        rcLeftView.setLayoutManager(new LinearLayoutManager(getActivity()));
        commonAdapter=  new MultiItemTypeAdapter(dataList) ;
        commonAdapter.addItemViewDelegate(new HomeLeftItemViewDelegate(getActivity()));
        commonAdapter.addItemViewDelegate(new HomeLeftLastItemViewDelegate(getActivity()));
        rcLeftView.setAdapter(commonAdapter);
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
                            case EventType.ACTION_LFET_DATA:
                                dataList.clear();
                                dataList.add(new ShopMainReponse.EntranceListBean("我的余额",leftData[0],null,null,false));
                                dataList.add(new ShopMainReponse.EntranceListBean("我的购买",leftData[1],null,null,false));
                                dataList.add(new ShopMainReponse.EntranceListBean("我的提货",leftData[2],null,null,false));
                                dataList.add(new ShopMainReponse.EntranceListBean("我的卡包",leftData[3],null,null,false));
                                dataList.add(new ShopMainReponse.EntranceListBean("银行卡",leftData[4],null,null,false));
                                dataList.add(new ShopMainReponse.EntranceListBean("我的地址",leftData[5],null,null,false));
                                for (ShopMainReponse.EntranceListBean listBean:( List<ShopMainReponse.EntranceListBean> )myEvent.object) {
                                    dataList.add(listBean);
                                }
                                dataList.add(new ShopMainReponse.EntranceListBean("系统设置",leftData[6],null,null,false));
                                commonAdapter.notifyDataSetChanged();
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
    public void doBusiness() {
        subscribeEvent();
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
        dataList.add(new ShopMainReponse.EntranceListBean("系统设置",leftData[6],null,null,false));
        commonAdapter.notifyDataSetChanged();
    }
}
