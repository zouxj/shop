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
import com.shenyu.laikaword.adapter.MultiItemTypeAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.module.mine.address.activity.AddAdressActivity;
import com.shenyu.laikaword.rxbus.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.zxj.utilslibrary.utils.IntentLauncher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends IKWordBaseFragment {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    CommonAdapter commonAdapter;
    @Override
    public int bindLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void doBusiness() {

        final List<String> list = new ArrayList<>();
        for (int i=0;i<60;i++){
            list.add("item"+i);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);
        commonAdapter=new CommonAdapter<String>(R.layout.item_home_shop,list) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.tv_text, o);
            }

            @Override
            public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
                super.setOnItemClickListener(onItemClickListener);

            }
        };
        recycleView.setAdapter(commonAdapter);
        RxBus.getDefault().toObservable(EventType.class).subscribe(new Action1<EventType>() {
            @Override
            public void call(EventType eventType) {
                switch (eventType.action){
                    case EventType.ACTION_LODE_MORE://上拉加载更多
                        list.addAll((List)eventType.object);
                        break;
                    case EventType.ACTION_PULL_REFRESH://下拉刷新
                        list.clear();
                        list.addAll((List)eventType.object);
                        break;
                }
                commonAdapter.notifyDataSetChanged();
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
