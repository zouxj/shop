package com.shenyu.laikaword.module.home.ui.fragment;


import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.MainViewPagerAdapter;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.helper.TabLayoutHelper;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.module.home.presenter.MainPresenter;
import com.shenyu.laikaword.module.home.view.MainView;
import com.shenyu.laikaword.module.us.message.UserMessageActivity;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.ui.view.widget.UPMarqueeView;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;


/**
 * 首页Fragment
 */
public class MainFragment extends IKWordBaseFragment implements MainView{

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager_bottom)
    ViewPager viewpager;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Inject
    MainViewPagerAdapter mainViewPagerAdapter;
    @Inject
    MainPresenter mainPresenter;
    @BindView(R.id.umt_main_gonggao)
    UPMarqueeView upMarqueeTextView;
    List<ShopMainReponse.PayloadBean.NoticeBean> data = new ArrayList<>();
    List<View> views = new ArrayList<>();
    BannerHelper bannerHelper;
    @BindView(R.id.bt_top_img)
    ImageView headImg;

    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView(View view){

        tabs.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutHelper.setIndicator(tabs, 25, 25);
            }
        });
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(false);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
//        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                mainPresenter.loadRefresh(MainFragment.this.bindToLifecycle());
//                ToastUtil.showToastShort("正在刷新...");
            }
        });
//        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000);
//                mainPresenter.onLoadMore();
////                ToastUtil.showToastShort("正在加载...");
//            }
//        });
        initViewpagerTop(view);
        subscribeEvent();

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
                                    ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(),headImg);
                                }else {
                                    headImg.setImageBitmap(null);
                                    headImg.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
                                }
                                break;
                        }
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
        setupViewPager();
        LoginReponse loginReponse = Constants.getLoginReponse();
        if (null!=loginReponse&&loginReponse.getPayload()!=null) {
            ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(), headImg);

        }
    }
    /**
     * 初始化头部效果
     */
    private void initViewpagerTop(View view) {
        bannerHelper  = BannerHelper.getInstance();
        bannerHelper.init(view.findViewById(R.id.banner_rootlayout));
        bannerHelper.setPoitSize(7);
        bannerHelper.setmPointersLayout(Gravity.RIGHT|Gravity.BOTTOM,240,0,15,15);
        bannerHelper.setIsAuto(true);

    }

    /**
     * 设置Top banner数据
     */
   public void setViewpagerTopData( List<ShopMainReponse.PayloadBean.BannerBean> bannerBeans){
       List<BannerBean> dataList = new ArrayList<>();
       if (null!=bannerBeans&&bannerBeans.size()>0) {
           for (ShopMainReponse.PayloadBean.BannerBean bannerBean:bannerBeans) {
               dataList.add(new BannerBean(R.mipmap.pager_one,bannerBean.getImageUrl() , "desc", bannerBean.getLink()));
           }
           bannerHelper.startBanner(dataList, new BannerHelper.OnItemClickListener() {
               @Override
               public void onItemClick(BannerBean bean) {
//                   IntentLauncher.with(getActivity()).put("weburl",bean.getDetailurl()).launch(GuessActivity.class);
                    IntentLauncher.with(getActivity()).launchViews(bean.getDetailurl());
               }
           });
       }
   }
@OnClick({R.id.bt_top_img,R.id.iv_message})
public void onClick(View v){
    switch (v.getId()){
        case R.id.bt_top_img:
            RxBus.getDefault().post(new Event(EventType.ACTION_OPONE_LEFT,""));
            break;
        case R.id.iv_message:
            IntentLauncher.with(getActivity()).launch(UserMessageActivity.class);
            break;
    }
}
    @Override
    public void setupFragmentComponent() {
        LaiKaApplication.get(getActivity()).getAppComponent().plus(new MainModule(this,getActivity())).inject(this);
    }

    @Override
    public void requestData() {

        mainPresenter.requestData(this.bindToLifecycle());
    }
    private void setupViewPager() {
        // 第二步：为ViewPager设置适配器
        viewpager.setAdapter(mainViewPagerAdapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tabs.setupWithViewPager(viewpager);
    }

    @Override
    public void isLoading() {
        loadViewHelper.showLoadingDialog(getActivity());
    }

    @Override
    public void dataCountChanged(int count) {

    }

    @Override
    public void loadFinished() {
        loadViewHelper.closeLoadingDialog();
    }

    @Override
    public void loadFailure() {
        smartRefreshLayout.setVisibility(View.GONE);
        loadViewHelper.showErrorResert(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smartRefreshLayout.setVisibility(View.VISIBLE);
                mainPresenter.requestData(MainFragment.this.bindToLifecycle());
            }
        });
    }


    @Override
    public void showShop(ShopMainReponse shopBeanReponse) {
        setViewpagerTopData(shopBeanReponse.getPayload().getBanner());
        SPUtil.saveObject(Constants.MAIN_SHOP_KEY,shopBeanReponse);
        RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE,shopBeanReponse.getPayload().getGoods()));
        data.addAll(shopBeanReponse.getPayload().getNotice());
        setNoticeView();
        mainPresenter.timeTask();
    }

    @Override
    public void loadMore(List list) {
        RxBus.getDefault().post(new Event(EventType.ACTION_LODE_MORE,list));
    }

    @Override
    public void refreshPull(List list) {
        RxBus.getDefault().post(new Event(EventType.ACTION_PULL_REFRESH,list));
    }
    private void setNoticeView() {
        for (int i = 0; i < data.size(); i = i + 2) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) UIUtil.inflate(R.layout.marquen_item);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.tv1);
            tv1.setText(data.get(i).getText());
            tv1.setTextSize(10);
            tv1.setTextColor(UIUtil.getColor(R.color.color_666));
            /**
             * 设置监听
             */
            final int finalI = i;
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentLauncher.with(getActivity()).launchViews(data.get(finalI).getLink());

                }
            });
            /**
             * 设置监听
             */
//            moreView.findViewById(R.id.rl2).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ToastUtil.showToastShort("你点击了" + data.get(position).toString());
//                }
//            });
//            //进行对控件赋值
//            tv1.setText(data.get(i).toString());
//            if (data.size() > i + 1) {
//                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
//                tv2.setText(data.get(i + 1).toString());
//            } else {
//                moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
//            }
            //添加到循环滚动数组里面去
            views.add(moreView);
            upMarqueeTextView.setViews(views);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerHelper.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerHelper.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bannerHelper.onDestroy();
        mainPresenter.detachView();
        mainPresenter.romveTask();
    }
}
