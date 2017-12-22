package com.shenyu.laikaword.module.us.goodcards.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PickUpActivity;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PickUpTelActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.UIUtil;

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
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(5),UIUtil.getColor(R.color.main_bg_gray)));
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter(R.layout.item_carpackage,dataList) {
            @Override
            protected void convert(ViewHolder holder, Object o, int position) {
//                holder.setText(R.id.tv_kpage_count,"");
//                holder.setText(R.id.tv_page_name,"");
//                holder.setText(R.id.iv_page_img,"");
                holder.setOnClickListener(R.id.tv_tihuo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 手机号和京东卡提货
                        if (false)
                        IntentLauncher.with(getActivity()).launch(PickUpActivity.class);
                        else
                            IntentLauncher.with(getActivity()).launch(PickUpTelActivity.class);
                    }
                });
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
