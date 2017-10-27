package com.shenyu.laikaword.model.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.shenyu.laikaword.module.us.goodcards.fragment.PurchaseListFragment;

/**
 * Created by shenyu_zxjCode on 2017/9/18 0018.
 */

public class PurchaseViewPagerAdapter extends BaseViewPagerAdapter {

    public PurchaseViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mlist = new String[]{"提货中","已提货"};
    }

    @Override
    public Fragment getItem(int position) {
        return new PurchaseListFragment(position);
    }
}
