package com.shenyu.laikaword.main.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.MainViewPagerAdapter;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.helper.TabLayoutHelper;
import com.shenyu.laikaword.main.MainModule;
import com.shenyu.laikaword.main.MainPresenter;
import com.shenyu.laikaword.main.MainView;
import com.shenyu.laikaword.module.mine.message.UserMessageActivity;
import com.shenyu.laikaword.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.rxbus.RxSubscriptions;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.widget.UPMarqueeView;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


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
    List<String> data = new ArrayList<>();
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
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadmore(false);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                mainPresenter.loadRefresh();
//                ToastUtil.showToastShort("正在刷新...");
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                mainPresenter.onLoadMore();
//                ToastUtil.showToastShort("正在加载...");
            }
        });
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
                                    Picasso.with(UIUtil.getContext()).load(loginReponse.getPayload().getAvatar()).placeholder(R.mipmap.left_user_icon)
                                            .error(R.mipmap.left_user_icon).resize(50, 50).transform(new CircleTransform()).into(headImg);
                                }else {
                                    headImg.setImageBitmap(null);
                                    headImg.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
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
        setupViewPager();
        LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
        if (null!=loginReponse&&loginReponse.getPayload()!=null) {
            mainPresenter.setImgHead(loginReponse.getPayload().getAvatar(), headImg);
            for (int i = 0; i < 2; i++) {
                data.add("版本更新啦,新会员更多福利" + i);
            }
            setNoticeView();
        }
    }
    /**
     * 初始化头部效果
     */
    private void initViewpagerTop(View view) {
        bannerHelper  = BannerHelper.getInstance();
        bannerHelper.init(view.findViewById(R.id.banner_rootlayout));
        bannerHelper.setmPointersLayout(Gravity.RIGHT|Gravity.BOTTOM);
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
                   Intent intent = new Intent();
                   intent.setAction("android.intent.action.VIEW");
                   Uri content_url = Uri.parse(bean.getDetailurl());
                   intent.setData(content_url);
                   startActivity(intent);
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

        mainPresenter.requestData();
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
                mainPresenter.requestData();
            }
        });
    }


    @Override
    public void showShop(ShopMainReponse shopBeanReponse) {
        setViewpagerTopData(shopBeanReponse.getPayload().getBanner());
        SPUtil.saveObject(Constants.MAIN_SHOP_KEY,shopBeanReponse.getPayload().getGoods());
        RxBus.getDefault().post(new Event(EventType.ACTION_MAIN_SETDATE,shopBeanReponse.getPayload().getGoods()));
//        mainPresenter.timeTask();
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
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) UIUtil.inflate(R.layout.marquen_item);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.tv1);
            tv1.setText(data.get(i));
            tv1.setTextSize(UIUtil.px2sp(getActivity(),36));
            tv1.setTextColor(UIUtil.getColor(R.color.color_666));
//            TextView tv2 = (TextView) moreView.findViewById(R.id.tv2);
            /**
             * 设置监听
             */
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 ToastUtil.showToastShort("你点击了" + data.get(position).toString());
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
