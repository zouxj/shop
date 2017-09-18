package com.shenyu.laikaword.module.mine.cards.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.helper.SpaceItemDecoration;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * create an instance of this fragment.
 */
public class PurchaseListFragment extends IKWordBaseFragment {

    @BindView(R.id.pirchase_ry_view)
    RecyclerView recyclerView;
    @Override
    public int bindLayout() {
        return R.layout.fragment_purchase_list;
    }

    @Override
    public void initView(View view) {
        recyclerView.addItemDecoration(new SpaceItemDecoration((int) UIUtil.dp2px(5)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<String> arry = new ArrayList<>();
        for (int i=0;i<10;i++){
            arry.add("item");
        }
        recyclerView.setAdapter(new CommonAdapter(R.layout.item_purchase,arry) {
            @Override
            protected void convert(ViewHolder holder, Object o, int position) {

            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

            }
        });
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
