package com.shenyu.laikaword.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.factory.FragmentGenerator;
import com.shenyu.laikaword.module.mine.cards.fragment.CarListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 * 卡包ViewPager适配器
 */

public class CarPackageViewPagerAdapter extends BaseViewPagerAdapter {
    public CarPackageViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mlist= new String[]{"全部","京东卡","移动卡","联通卡","电信卡"};

    }

    @Override
    public Fragment getItem(int position) {
        return   new CarListFragment();
    }
}
