package com.shenyu.laikaword.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.bean.reponse.CarPagerReponse;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public abstract class BaseViewPager<T> {
    public Activity mActivity;
    public View mRootView;
    public FrameLayout flContent;//内容
    public BaseViewPager(Activity activity){
        this.mActivity=activity;
        init();

    }
    private void init() {
        mRootView = UIUtil.inflate(R.layout.baer_view_pager);
        flContent = mRootView.findViewById(R.id.fl_content);
        flContent.removeAllViews();
        flContent.addView(initView());
    }
    public abstract View initView();
    public void initData(T t,int postion){

    }
    public void initData(){

    }
}
