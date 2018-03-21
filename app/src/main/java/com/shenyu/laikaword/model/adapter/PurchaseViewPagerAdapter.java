package com.shenyu.laikaword.model.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.shenyu.laikaword.model.bean.reponse.PurChaseReponse;
import com.shenyu.laikaword.module.us.goodcards.fragment.PurchaseListFragment;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/9/18 0018.
 */

public class PurchaseViewPagerAdapter extends BaseViewPagerAdapter {
    private  List<PurChaseReponse.PayloadBean> goodType;
    public PurchaseViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }
    public void setDataList(String[] mListData, List<PurChaseReponse.PayloadBean> goodType){
        this.mlist=mListData;
        this.goodType=goodType;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {

        return new PurchaseListFragment(goodType.get(position));
    }
}
