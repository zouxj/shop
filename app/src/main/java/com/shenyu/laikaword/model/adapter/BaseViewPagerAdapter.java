package com.shenyu.laikaword.model.adapter;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zxj.utilslibrary.utils.LogUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 */

public class BaseViewPagerAdapter extends FragmentPagerAdapter {
    public String[] mlist={};
    public BaseViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (null!=mlist&&mlist.length>0) {
            return mlist[position];
        }else {
            return null;
        }
    }
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mlist.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtil.i(position+"_____fragment");
        return super.instantiateItem(container, position);
    }

}
