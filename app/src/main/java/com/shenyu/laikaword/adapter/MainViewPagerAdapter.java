package com.shenyu.laikaword.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.shenyu.laikaword.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.main.fragment.ListFragment;
import com.shenyu.laikaword.rxbus.EventType;
import com.shenyu.laikaword.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

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
            return new ListFragment(position);
        }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
