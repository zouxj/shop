package com.shenyu.laikaword.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenyu.laikaword.interfaces.IBaseFragment;
import com.zxj.utilslibrary.utils.LogUtil;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class IKWordBaseFragment extends Fragment implements IBaseFragment {
    protected final String TAG = this.getClass().getSimpleName();
    private View mContentView = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.d(TAG, TAG + "-->onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, TAG + "-->onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(TAG, TAG + "-->onCreateView()");
        // 渲染视图View(防止切换时重绘View)
        if (null != mContentView) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (null != parent) {
                parent.removeView(mContentView);
            }
        } else {
            mContentView = inflater.inflate(bindLayout(), container);
        }
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtil.d(TAG, TAG + "-->onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        doBusiness(getActivity());// 业务处理
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        LogUtil.d(TAG, TAG + "-->onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        LogUtil.d(TAG, TAG + "-->onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        LogUtil.d(TAG, TAG + "-->onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtil.d(TAG, TAG + "-->onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtil.d(TAG, TAG + "-->onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtil.d(TAG, TAG + "-->onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, TAG + "-->onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogUtil.d(TAG, TAG + "-->onDetach()");
        super.onDetach();
    }


    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void doBusiness(Context context) {

    }
}
