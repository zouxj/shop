package com.shenyu.laikaword.main.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.adapter.wrapper.HeaderAndFooterWrapper;
import com.shenyu.laikaword.adapter.wrapper.LoadMoreWrapper;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.widget.LinearCheckContainerView;
import com.shenyu.laikaword.widget.LinearContainerView;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopFragment extends IKWordBaseFragment {
    @Override
    public int bindLayout() {
        return R.layout.fragment_shop;
    }

    @Override
    public void doBusiness() {


    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {

    }
}
