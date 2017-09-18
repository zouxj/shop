package com.shenyu.laikaword.module.mine.cards.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarListFragment extends IKWordBaseFragment {

    @BindView(R.id.rlv_view)
    RecyclerView recyclerView;
    @Override
    public int bindLayout() {
        return R.layout.fragment_car_list;
    }

    @Override
    public void initView(View view) {
        List<String> dataList = new ArrayList<>();
        for (int i=0;i<10;i++){
            dataList.add("item");
        }
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter(R.layout.item_carpackage,dataList) {
            @Override
            protected void convert(ViewHolder holder, Object o, int position) {

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
