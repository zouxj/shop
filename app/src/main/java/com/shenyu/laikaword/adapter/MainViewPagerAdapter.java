package com.shenyu.laikaword.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.shenyu.laikaword.base.BasePagerAdapter;
import com.shenyu.laikaword.base.BaseViewPager;
import com.shenyu.laikaword.main.fragment.MainListViewPager;

/**
 * Created by Administrator on 2017/9/13 0013.
 */
   public class MainViewPagerAdapter extends BasePagerAdapter{

    public MainViewPagerAdapter(Activity activity, String[] listData) {
        super(activity, listData);
    }


    @Override
    public int getCount() {
        return mListData.length;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseViewPager pager = new MainListViewPager(mActivity,position);
        container.addView(pager.mRootView);
        pager.initData();
        return pager.mRootView;
    }


}
