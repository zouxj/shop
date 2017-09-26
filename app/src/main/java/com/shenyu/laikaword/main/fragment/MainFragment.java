package com.shenyu.laikaword.main.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.shenyu.laikaword.bean.reponse.ShopBeanReponse;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.main.MainModule;
import com.shenyu.laikaword.main.MainPresenter;
import com.shenyu.laikaword.main.MainView;
import com.shenyu.laikaword.main.activity.MainActivity;
import com.shenyu.laikaword.rxbus.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.widget.UPMarqueeView;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

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
    List<String> data = new ArrayList<>();
    List<View> views = new ArrayList<>();
    BannerHelper bannerHelper;
    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView(View view){
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
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

    }
    @Override
    public void doBusiness() {
        setupViewPager();
        for(int i=0;i<2;i++){
            data.add("版本更新啦,新会员更多福利"+i);
        }
        setNoticeView();
    }

    /**
     * 初始化头部效果
     */
    private void initViewpagerTop(View view) {
        List<BannerBean> dataList = new ArrayList<>();
        dataList.add(new BannerBean(R.mipmap.pager_one, "url", "desc", "datailurl"));
        bannerHelper  = BannerHelper.getInstance();
        bannerHelper.init(view.findViewById(R.id.banner_rootlayout));
        bannerHelper.setmPointersLayout(Gravity.RIGHT|Gravity.BOTTOM);
        bannerHelper.startBanner(dataList, new BannerHelper.OnItemClickListener() {
            @Override
            public void onItemClick(BannerBean bean) {
                ToastUtil.showToastShort(bean.getDesc());
            }
        });
    }
@OnClick({R.id.bt_top_img})
public void onClick(View v){
    switch (v.getId()){
        case R.id.bt_top_img:
            RxBus.getDefault().post(new EventType(EventType.ACTION_OPONE_LEFT,""));
            break;
    }
}
    @Override
    public void setupFragmentComponent() {
        LaiKaApplication.get(getActivity()).getAppComponent().plus(new MainModule(this,getFragmentManager())).inject(this);
    }

    @Override
    public void requestData() {

    }
    private void setupViewPager() {
        // 第二步：为ViewPager设置适配器
        viewpager.setAdapter(mainViewPagerAdapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tabs.setupWithViewPager(viewpager);
    }


    @Override
    public void isLoading() {

    }

    @Override
    public void dataCountChanged(int count) {

    }

    @Override
    public void loadFinished() {

    }

    @Override
    public void showShop(ShopBeanReponse shopBeanReponse) {

    }

    @Override
    public void loadMore(List list) {
        RxBus.getDefault().post(new EventType(EventType.ACTION_LODE_MORE,list));
    }

    @Override
    public void refreshPull(List list) {
        RxBus.getDefault().post(new EventType(EventType.ACTION_PULL_REFRESH,list));
    }
    private void setNoticeView() {
        for (int i = 0; i < data.size(); i = i + 2) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) UIUtil.inflate(R.layout.marquen_item);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            tv1.setText(data.get(i));
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
    }
}
