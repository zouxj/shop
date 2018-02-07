package com.shenyu.laikaword.module.home.ui.fragment;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.helper.StatusBarManager;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.MainViewPagerAdapter;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.helper.TabLayoutHelper;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.module.home.presenter.MainPresenter;
import com.shenyu.laikaword.module.home.view.MainView;
import com.shenyu.laikaword.module.login.ui.activity.LoginActivity;
import com.shenyu.laikaword.module.us.message.UserMessageActivity;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.module.us.resell.ui.activity.ResellInputCodeActivity;
import com.shenyu.laikaword.ui.view.widget.UPMarqueeView;
import com.shenyu.laikaword.model.web.GuessActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


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
    @BindView(R.id.id_title)
    RelativeLayout relativeLayout;
    @BindView(R.id.tv_point)
    ImageView ivPoint;
    @BindView(R.id.iv_message)
    ImageView imageMessage;

    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView(View view){

        StatusBarManager statusBarManager = new StatusBarManager(getActivity(),UIUtil.getColor(R.color.app_theme_red));
            int statusBarHeight=  statusBarManager.getStatusBarHeight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
            params.topMargin=statusBarHeight;
            relativeLayout.setLayoutParams(params);
        }
        if (tabs!=null) {
            tabs.post(new Runnable() {
                @Override
                public void run() {
                    TabLayoutHelper.setIndicator(tabs, 15, 15);
                }
            });
        }
        smartRefreshLayout.setHeaderHeight(45);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1500);
                mainPresenter.loadRefresh(MainFragment.this.bindToLifecycle());
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1500);
                mainPresenter.onLoadMore(MainFragment.this.bindToLifecycle(),viewpager.getCurrentItem());
            }
        });
        initViewpagerTop(view);


    }




    @Override
    public void doBusiness() {
        setupViewPager();
        LoginReponse loginReponse = Constants.getLoginReponse();
        if (null!=loginReponse&&loginReponse.getPayload()!=null) {
            ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(), headImg);

        }

        mainPresenter.requestData(bindToLifecycle());
        mainPresenter.timeTask(bindToLifecycle());
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
               dataList.add(new BannerBean(R.mipmap.defaul_icon,bannerBean.getImageUrl() , "desc", bannerBean.getLink()));
           }
           bannerHelper.setLockTime(10000);
           bannerHelper.startBanner(dataList, new BannerHelper.OnItemClickListener() {
               @Override
               public void onItemClick(BannerBean bean) {
                   IntentLauncher.with(getActivity()).put("weburl",bean.getDetailurl()).launch(GuessActivity.class);
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
            LoginReponse loginReponse = Constants.getLoginReponse();
            if (null==loginReponse) {
                IntentLauncher.with(getActivity()).launch(LoginActivity.class);
                return;

            }
            if (resellshow==0){
                IntentLauncher.with(getActivity()).launch(UserMessageActivity.class);
            }else {
                IntentLauncher.with(getActivity()).launch(ResellInputCodeActivity.class);
            }



            break;
    }
}
    @Override
    public void setupFragmentComponent() {
        LaiKaApplication.get(getActivity()).getAppComponent().plus(new MainModule(this,getActivity())).inject(this);
    }

    @Override
    public void requestData() {
//        mainPresenter.requestData(this.bindToLifecycle());
    }
    private void setupViewPager() {
        // 第二步：为ViewPager设置适配器
        viewpager.setAdapter(mainViewPagerAdapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tabs.setupWithViewPager(viewpager);
    }

    @Override
    public void isLoading() {
//        loadViewHelper.showLoadingDialog(getActivity());
    }

    @Override
    public void loadSucceed(BaseReponse baseReponse) {
//        loadViewHelper.closeLoadingDialog();
    }


    @Override
    public void loadFailure() {
//        loadViewHelper.closeLoadingDialog();
//        loadViewHelper.showErrorResert(getActivity(), new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadViewHelper.showLoadingDialog(getActivity());
//                mainPresenter.requestData(MainFragment.this.bindToLifecycle());
//            }
//        });
    }
    private  int resellshow = 0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void showShop(ShopMainReponse shopBeanReponse) {


        setViewpagerTopData(shopBeanReponse.getPayload().getBanner());
        //是否显示转卖
            if (shopBeanReponse.getPayload().getResellDisplay()==0){
                resellshow=0;
                if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                    imageMessage.setBackground(UIUtil.getDrawable(R.mipmap.app_main_message_icon));
                else
                    imageMessage.setBackgroundResource(R.mipmap.app_main_message_icon);
            }else {
                resellshow=1;
                if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                    imageMessage.setBackground(UIUtil.getDrawable(R.mipmap.exchange));
                else
                    imageMessage.setBackgroundResource(R.mipmap.exchange);
            }
        /**
         * 判断第一次进入app,选择进入
         */
        if (!StringUtil.validText(SPUtil.getString("start_app_t", ""))) {
            //TODO 第一次登录
            loadViewHelper.maskView(getActivity(),resellshow);
            SPUtil.putString("start_app_t", "one");
        }

        if (null!=shopBeanReponse.getPayload().getEntranceList()) {
            if (shopBeanReponse.getPayload().getEntranceList().size() > 0) {
                ivPoint.setVisibility(shopBeanReponse.getPayload().getFlag().getnewExtractFlag().equals("1") ? View.VISIBLE : View.GONE);
                RxBus.getDefault().post(new Event(EventType.ACTION_LFET_DATA, shopBeanReponse.getPayload().getEntranceList()));
            }

            data.clear();
            data.addAll(shopBeanReponse.getPayload().getNotice());
            setNoticeView();
        }
        viewpager.setCurrentItem(mainPresenter.feileiItem(shopBeanReponse));

    }



    @Override
    public void loadMore(List list) {
    if (list.size()<=0) {
        ToastUtil.showToastShort("没有更多数据了!");
    }
    else {
        Map<Integer, List> listMap = new HashMap<>();
        listMap.put(viewpager.getCurrentItem(), list);
        RxBus.getDefault().post(new Event(EventType.ACTION_LODE_MORE, listMap));
    }
    }

    @Override
    public void refreshPull(List list) {
        RxBus.getDefault().post(new Event(EventType.ACTION_PULL_REFRESH,list));
    }

    @Override
    public void subscribeEvent(Event event) {
        switch (event.event) {
            case EventType.ACTION_UPDATA_USER:
                LoginReponse loginReponse = Constants.getLoginReponse();
                if (null!=loginReponse) {
                    ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(),headImg);
                }else {
                    headImg.setImageBitmap(null);
                    if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                        headImg.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
                    else
                        headImg.setBackgroundResource(R.mipmap.left_user_icon);
                }
                break;
        }
    }

    private void setNoticeView() {
        views.clear();
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
                    IntentLauncher.with(getActivity()).put("weburl",data.get(finalI).getLink()).launch(GuessActivity.class);
            }
            });
            views.add(moreView);
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

        }
        upMarqueeTextView.setViews(views);
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerHelper.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        smartRefreshLayout.autoRefresh();
        bannerHelper.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bannerHelper.onDestroy();
        mainPresenter.detachView();
    }
}
