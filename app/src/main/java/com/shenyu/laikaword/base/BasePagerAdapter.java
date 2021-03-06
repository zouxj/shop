package com.shenyu.laikaword.base;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public class BasePagerAdapter extends PagerAdapter {
    public String[] mListData={};
    public Activity mActivity;

    public BasePagerAdapter(Activity activity, String[] listData) {
        this.mListData=mListData;
        this.mActivity=activity;
    }
    public BasePagerAdapter(Activity activity){
        this.mActivity=activity;
    }
    public void setDataList(String[] mListData){
        this.mListData=mListData;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mListData.length;
    }
    
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListData[position];
    }
}
