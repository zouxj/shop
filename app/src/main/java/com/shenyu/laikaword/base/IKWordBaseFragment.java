package com.shenyu.laikaword.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenyu.laikaword.helper.LoadViewHelper;
import com.shenyu.laikaword.Interactor.IBaseFragment;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.zxj.utilslibrary.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Fragment基类
 */
public abstract class IKWordBaseFragment extends RxFragment implements IBaseFragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected View mContentView = null;
    private Unbinder unbinder;
    protected boolean isViewInitiated;
    protected boolean isDataLoaded;
    protected Subscription mRxSub;
    public LoadViewHelper loadViewHelper;
    protected RetrofitUtils retrofitUtils;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 渲染视图View(防止切换时重绘View)
        if (null != mContentView) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (null != parent) {
                parent.removeView(mContentView);
            }
        } else {
            mContentView = inflater.inflate(bindLayout(), container,false);
        }
        unbinder = ButterKnife.bind(this, mContentView);
        initView(mContentView);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtil.d(TAG, TAG + "-->onViewCreated()");
        isViewInitiated = true;
        retrofitUtils=RetrofitUtils.getRetrofitUtils().setLifecycleTransformer(this.bindToLifecycle());
        prepareRequestData();
        super.onViewCreated(view, savedInstanceState);
        doBusiness();// 业务处理

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.i("isVisibleToUser=>"+isVisibleToUser);
        prepareRequestData();
    }

    public abstract void requestData();

    public boolean prepareRequestData() {
        return prepareRequestData(true);
    }

    public boolean prepareRequestData(boolean forceUpdate) {
        if (getUserVisibleHint() && isViewInitiated && (!isDataLoaded || forceUpdate)) {
            loadViewHelper = LoadViewHelper.instanceLoadViewHelper();
            setupFragmentComponent();
            requestData();
            return true;
        }
        return false;
    }

    /**
     * 初始化布局
     */
    public void initView(View view){}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRxSub!=null)
        RxSubscriptions.remove(mRxSub);
    }
}
