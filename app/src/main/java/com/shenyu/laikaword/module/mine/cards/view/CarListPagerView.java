package com.shenyu.laikaword.module.mine.cards.view;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.BaseViewPager;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.module.shop.activity.PickUpActivity;
import com.shenyu.laikaword.module.shop.activity.PickUpTelActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public class CarListPagerView extends BaseViewPager {
    RecyclerView recyclerView;

    public CarListPagerView(Activity activity) {
        super(activity);
    }


    @Override
    public View initView() {
        View view = UIUtil.inflate(R.layout.fragment_car_list);
        recyclerView = view.findViewById(R.id.rlv_view);
        return view;
    }

    @Override
    public void initData() {
        List<String> dataList = new ArrayList<>();
        for (int i=0;i<10;i++){
            dataList.add("item");
        }
        recyclerView.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(5),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
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
                            IntentLauncher.with(mActivity).launch(PickUpActivity.class);
                        else
                            IntentLauncher.with(mActivity).launch(PickUpTelActivity.class);
                    }
                });
            }

        });
    }
}
