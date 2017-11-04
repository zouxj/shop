package com.shenyu.laikaword.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.ui.view.widget.loaddialog.ProgressLayout;

/**
 * Created by shenyu_zxjCode on 2017/10/17 0017.
 */

public class LoadViewHelper {


    private  static  LoadViewHelper loadViewHelper;
    private LoadViewHelper(){}
    public static LoadViewHelper instanceLoadViewHelper(){
        if (null==loadViewHelper){
            synchronized (LoadViewHelper.class){
                if (null==loadViewHelper){
                    loadViewHelper = new LoadViewHelper();
                }
            }
        }
        return loadViewHelper;
    }

    /**
     *
     * Description:显示加载
     *
     * @author zxj 2015-5-25
     */
    ProgressLayout loadingView = null;
    public void showLoadingDialog(Activity activity) {
        if (loadingView == null) {
            loadingView = (ProgressLayout) LayoutInflater.from(activity).inflate(
                    R.layout.include_empty_view, null);
            loadingView.showLoading();
            ((ViewGroup) activity.getWindow().getDecorView()).addView(loadingView);
        }
        loadingView.setVisibility(View.VISIBLE);
    }


    /**
     *
     * Description:关闭加载
     *
     * @author zxj 2015-5-25
     */
    public void closeLoadingDialog() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
    }
    public void showEmpty(String msg, Activity activity) {
            loadingView = (ProgressLayout) LayoutInflater.from(activity).inflate(
                    R.layout.include_empty_view, null);
            loadingView.showEmpty(R.mipmap.net_error_icon,msg);
            ((ViewGroup) activity.getWindow().getDecorView()).addView(loadingView);

    }

    public void showErrorResert(Activity activity ,View.OnClickListener onClickListener) {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        loadingView = (ProgressLayout) LayoutInflater.from(activity).inflate(R.layout.include_empty_view, null);
        loadingView.showError(onClickListener);
        ((ViewGroup) activity.getWindow().getDecorView()).addView(loadingView);
    }
    LinearLayout linearLayout;

    public void  maskView(Activity activity){
        linearLayout= (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.masking_layout, null);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.GONE);
            }
        });
        ((ViewGroup) activity.getWindow().getDecorView()).addView(linearLayout);
    }
}
