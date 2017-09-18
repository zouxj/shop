package com.shenyu.laikaword.main.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


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
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

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


    }
    @Override
    public void doBusiness() {
        initViewpagerTop();
        setupViewPager();
    }

    /**
     * 初始化头部效果
     */
    private void initViewpagerTop() {
        List<BannerBean> dataList = new ArrayList<>();
        dataList.add(new BannerBean(R.mipmap.ic_launcher, "http://img-ws.doupai.cc/Ie548c2459f324e6f79180d6eb61d78be.jpg!avatar", null, null));
        dataList.add(new BannerBean(R.mipmap.ic_launcher, "http://img-ws.doupai.cc/I2a776ee24c2b3adfa62288bb252bb68a.jpg!avatar", null, null));
        dataList.add(new BannerBean(R.mipmap.ic_launcher, "http://img-ws.doupai.cc/Ia0003e4a1ac7d9c8e1d48bca6235f599.jpg?imageView2/1/w/200", null, null));
        dataList.add(new BannerBean(R.mipmap.ic_launcher, "http://img-ws.doupai.cc/Ia0003e4a1ac7d9c8e1d48bca6235f599.jpg!origin", null, null));
        BannerHelper bannerHelper  = BannerHelper.getInstance().init(mContentView.findViewById(R.id.banner_rootlayout));
        bannerHelper.setmPointersLayout(Gravity.RIGHT|Gravity.BOTTOM);
        bannerHelper.setIsAuto(true);
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

}
