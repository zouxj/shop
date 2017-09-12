package com.shenyu.laikaword.main.fragment;


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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends IKWordBaseFragment {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    public int bindLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void doBusiness() {
        List<String> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add("item"+i);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(new CommonAdapter<String>(R.layout.item_home_shop,list) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.tv_text, o);
            }

        });
    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {

    }
}
