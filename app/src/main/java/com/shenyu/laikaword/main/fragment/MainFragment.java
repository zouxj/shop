package com.shenyu.laikaword.main.fragment;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.helper.BannerBean;
import com.shenyu.laikaword.helper.BannerHelper;
import com.shenyu.laikaword.rxbus.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 首页Fragment
 */
public class MainFragment extends IKWordBaseFragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager_bottom)
    ViewPager viewpager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    String[] mTitles = new String[]{
            "主页", "微博", "相册"
    };
    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
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

    }

    @Override
    public void requestData() {

    }
    private void setupViewPager() {

        for (int i = 0; i < mTitles.length; i++) {

        }
        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getFragmentManager());

        viewpager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tabs.setupWithViewPager(viewpager);
    }
    class BaseFragmentAdapter extends FragmentPagerAdapter {

        public BaseFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
        @Override
        public Fragment getItem(int position) {
            return new ListFragment();
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LogUtil.i(position+"_____fragment");
            return super.instantiateItem(container, position);
        }
    }

}
