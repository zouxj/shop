package com.shenyu.laikaword.model.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import com.shenyu.laikaword.base.BasePagerAdapter;
import com.shenyu.laikaword.base.BaseViewPager;
import com.shenyu.laikaword.model.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.module.us.goodcards.ui.CarListPagerView;


/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 * 卡包ViewPager适配器
 */

public class CarPackageViewPagerAdapter extends BasePagerAdapter {

    private CarPagerReponse mCarPagerReonse;

    public CarPackageViewPagerAdapter(Activity activity) {
        super(activity);
    }


    public void setData(CarPagerReponse carPagerReponse){
        this.mCarPagerReonse=carPagerReponse;
        if (null!=carPagerReponse){
            mListData=new String[mCarPagerReonse.getPayload().size()+1];
            mListData[0]="全部";
            for (int i=0;i<mCarPagerReonse.getPayload().size();i++){
                mListData[i+1]=mCarPagerReonse.getPayload().get(i).getName();
            }
        }
        notifyDataSetChanged();


    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseViewPager pager = new CarListPagerView(mActivity);
        container.addView(pager.mRootView);
        pager.initData(mCarPagerReonse,mListData[position]);
        return pager.mRootView;
    }
}
