package com.shenyu.laikaword.module.shop.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.helper.SpaceItemDecoration;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class BuyGoodsActivity extends LKWordBaseActivity {

    @BindView(R.id.rcl_goods)
    RecyclerView recyclerView;

    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up_goods;
    }

    @Override
    public void initView() {
        setToolBarTitle("我的购买");
        List<String> dataList = new ArrayList<>();
        for (int i=0;i<10;i++){
            dataList.add("item");
        }

        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(9),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonAdapter(R.layout.item_goods,dataList) {
            @Override
            protected void convert(ViewHolder holder, Object o, int position) {
//                holder.setText(R.id.tv_goumai_count,"");
//                holder.setText(R.id.tv_goumai_indent_no,"");
//                holder.setText(R.id.tv_goumai_shop_name,"");
//                holder.setText(R.id.tv_goumai_shop_original_price,"");
//                holder.setText(R.id.tv_goumai_shop_price,"");
//                holder.setText(R.id.tv_goumai_time,"");
            }
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }
        });
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }
}
