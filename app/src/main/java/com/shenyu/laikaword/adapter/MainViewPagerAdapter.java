package com.shenyu.laikaword.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shenyu.laikaword.main.fragment.ListFragment;
import com.zxj.utilslibrary.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13 0013.
 */
   public class MainViewPagerAdapter extends BaseViewPagerAdapter{

        public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
          mlist  = new String[]{"移动卡", "京东卡", "联通卡", "电信卡"};
         }


        @Override
        public Fragment getItem(int position) {
            return new ListFragment();
        }


}
