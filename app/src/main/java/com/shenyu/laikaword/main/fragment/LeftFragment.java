package com.shenyu.laikaword.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.MultiItemTypeAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.module.login.address.EditeAddressActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LeftFragment extends IKWordBaseFragment {

    @BindView(R.id.tv_user_head)
    TextView tvUserHead;
    @BindView(R.id.rc_left_view)
    RecyclerView rcLeftView;

    @Override
    public int bindLayout() {
        return R.layout.fragment_left;
    }

    @Override
    public void doBusiness() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0;i<6;i++){
            dataList.add("item"+i);
        }
        rcLeftView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcLeftView.setAdapter(new CommonAdapter<String>(R.layout.item_left_frame,dataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_left_menu,s);
            }

            @Override
            protected void setListener(ViewGroup parent, ViewHolder viewHolder, int viewType) {

//             IntentLauncher.with(getActivity()).launch(EditeAddressActivity.class);
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
