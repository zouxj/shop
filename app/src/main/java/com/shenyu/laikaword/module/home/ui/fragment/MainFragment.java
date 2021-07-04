package com.shenyu.laikaword.module.home.ui.fragment;


import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shenyu.laikaword.helper.GridSpacingItemDecoration;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.helper.StatusBarManager;
import com.shenyu.laikaword.model.adapter.HomeAdapter;
import com.shenyu.laikaword.model.adapter.MultiItemTypeAdapter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.module.home.ui.activity.GoodsDetailsActivity;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.di.module.MainModule;
import com.shenyu.laikaword.module.home.presenter.MainPresenter;
import com.shenyu.laikaword.module.home.view.MainView;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 首页Fragment
 */
public class MainFragment extends IKWordBaseFragment implements MainView {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Inject
    MainPresenter mainPresenter;
    List<ShopMainReponse.PayloadBean.NoticeBean> data = new ArrayList<>();
    @BindView(R.id.bt_top_img)
    ImageView headImg;
    @BindView(R.id.id_title)
    RelativeLayout relativeLayout;
    @BindView(R.id.tv_point)
    ImageView ivPoint;
    @BindView(R.id.iv_message)
    ImageView imageMessage;
    @BindView(R.id.recy_home)
    RecyclerView mRecyHome;

    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView(View view) {

        StatusBarManager statusBarManager = new StatusBarManager(getActivity(), UIUtil.getColor(R.color.app_theme_red));
        int statusBarHeight = statusBarManager.getStatusBarHeight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
            params.topMargin = statusBarHeight;
            relativeLayout.setLayoutParams(params);
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
                //TODO 加载更多

            }
        });

        mRecyHome.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyHome.addItemDecoration(new GridSpacingItemDecoration(2, (int) UIUtil.dp2px(10), true));
        List<String> date = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int x = new Random().nextInt(350) + 100;
            date.add(x + "");

        }
        HomeAdapter homeAdapter = new HomeAdapter(R.layout.item_new_goods, date);
        mRecyHome.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                IntentLauncher.with(getActivity()).launch(GoodsDetailsActivity.class);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @Override
    public void doBusiness() {
        LoginReponse loginReponse = Constants.getLoginReponse();
        if (null != loginReponse && loginReponse.getPayload() != null) {
            ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(), headImg);

        }

        mainPresenter.requestData(bindToLifecycle());
        mainPresenter.timeTask(bindToLifecycle());
    }

    @OnClick({R.id.bt_top_img})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_top_img:
                RxBus.getDefault().post(new Event(EventType.ACTION_OPONE_LEFT, ""));
                break;

        }
    }

    @Override
    public void setupFragmentComponent() {
        LaiKaApplication.get(getActivity()).getAppComponent().plus(new MainModule(this, getActivity())).inject(this);
    }

    @Override
    public void requestData() {
    }

    @Override
    public void isLoading() {
    }

    @Override
    public void loadSucceed(BaseReponse baseReponse) {
    }


    @Override
    public void loadFailure() {

    }

    private int resellshow = 0;

    @Override
    public void showShop(ShopMainReponse shopBeanReponse) {

        /**
         * 判断第一次进入app,选择进入
         */
        if (!StringUtil.validText(SPUtil.getString("start_app_t", ""))) {
            //TODO 第一次登录
            loadViewHelper.maskView(getActivity(), resellshow);
            SPUtil.putString("start_app_t", "one");
        }

        if (null != shopBeanReponse.getPayload().getEntranceList()) {
            if (shopBeanReponse.getPayload().getEntranceList().size() > 0) {
                ivPoint.setVisibility(shopBeanReponse.getPayload().getFlag().getnewExtractFlag().equals("1") ? View.VISIBLE : View.GONE);
                RxBus.getDefault().post(new Event(EventType.ACTION_LFET_DATA, shopBeanReponse.getPayload().getEntranceList()));
            }

            data.clear();
            data.addAll(shopBeanReponse.getPayload().getNotice());
        }

    }


    @Override
    public void loadMore(List list) {
        if (list.size() <= 0) {
            ToastUtil.showToastShort("没有更多数据了!");
        } else {
            //TODO
        }
    }

    @Override
    public void refreshPull(List list) {
        RxBus.getDefault().post(new Event(EventType.ACTION_PULL_REFRESH, list));
    }

    @Override
    public void subscribeEvent(Event event) {
        switch (event.event) {
            case EventType.ACTION_UPDATA_USER:
                LoginReponse loginReponse = Constants.getLoginReponse();
                if (null != loginReponse) {
                    ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(), headImg);
                } else {
                    headImg.setImageBitmap(null);
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                        headImg.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
                    else
                        headImg.setBackgroundResource(R.mipmap.left_user_icon);
                }
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }
}
