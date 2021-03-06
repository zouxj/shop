package com.shenyu.laikaword.model.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.shenyu.laikaword.module.us.resell.ui.fragment.ResellFragment;

/**
 * Created by shenyu_zxjCode on 2017/12/13 0013.
 */

public class ZhuanMaiViewAdapter  extends BaseViewPagerAdapter{
        public ZhuanMaiViewAdapter(FragmentManager fm) {
            super(fm);
            mlist = new String[]{"转卖中","已转卖"};
        }

        @Override
        public Fragment getItem(int position) {
            return new ResellFragment(position);
        }

}
