package com.shenyu.laikaword.main.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shenyu.laikaword.main.fragment.OwnFragment;
import com.shenyu.laikaword.main.fragment.ShopFragment;
import com.shenyu.laikaword.main.fragment.SnatchFragment;
import com.zxj.utilslibrary.utils.LogUtil;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class MainPageAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"商城", "夺宝", "我的"};
    private final String TAG =this.getClass().getSimpleName();
    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }
    private Fragment mCurrentPrimaryItem;

    @Override
    public Fragment getItem(int position) {
        switch (position){
//            case 0:
//                mCurrentPrimaryItem =  new ShopFragment();
//                LogUtil.i("ShopFragment");
//                break;
//            case 1:
//                mCurrentPrimaryItem =  new SnatchFragment();
//                LogUtil.i("SnatchFragment");
//                break;
//            case 2:
//                mCurrentPrimaryItem =  new OwnFragment();
//                LogUtil.i("OwnFragment");
//                break;
        }
        return mCurrentPrimaryItem;
    }

    @Override
    public int getCount() {

        return mTitles.length;
    }
    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtil.i(position+"_____fragment");
        return super.instantiateItem(container, position);
    }
}
