package com.shenyu.laikaword.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.shenyu.laikaword.base.BasePagerAdapter;
import com.shenyu.laikaword.base.BaseViewPager;
import com.shenyu.laikaword.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.module.mine.cards.view.CarListPagerView;


/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 * 卡包ViewPager适配器
 */

public class CarPackageViewPagerAdapter extends BasePagerAdapter {

    private CarPagerReponse mCarPagerReonse;
    public CarPackageViewPagerAdapter(Activity activity, String[] listData) {
        super(activity, listData);
    }

    public void setData(CarPagerReponse carPagerReponse){
        this.mCarPagerReonse=carPagerReponse;
        notifyDataSetChanged();


    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseViewPager pager = new CarListPagerView(mActivity);
        container.addView(pager.mRootView);
        pager.initData(mCarPagerReonse,position);
        return pager.mRootView;
    }
}
