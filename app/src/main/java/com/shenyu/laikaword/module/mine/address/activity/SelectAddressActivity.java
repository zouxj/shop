package com.shenyu.laikaword.module.mine.address.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectAddressActivity extends LKWordBaseActivity {


    @BindView(R.id.re_cy_view)
    RecyclerView reCyView;

    @Override
    public int bindLayout() {
        return R.layout.activity_select_address;
    }

    @Override
    public void initView() {
        setToolBarTitle("选择地址");
        setToolBarRight("管理");
        reCyView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(5),UIUtil.getColor(R.color.main_bg_gray)));
        reCyView.setLayoutManager(new LinearLayoutManager(this));
    }
    CommonAdapter<String> commonAdapter;
    @Override
    public void doBusiness(Context context) {
        final List<String> dataList = new ArrayList<>();
        for (int i=0;i<9;i++){
            dataList.add("item"+i);
        }
          commonAdapter=   new CommonAdapter<String>(R.layout.item_select_address,dataList) {
            int selectedPosition=-1;
            @Override
            protected void convert(final ViewHolder holder, String s, final int position) {
                CheckBox checkBox = holder.getView(R.id.ck_select_address);
                checkBox.setChecked(selectedPosition==position?true:false);
                holder.itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition!=position){
                            //先取消上个item的勾选状态
                            if (selectedPosition!=-1) {
                                holder.itemView.setSelected(false);
                                notifyItemChanged(selectedPosition);
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            holder.itemView.setSelected(true);
                            notifyItemChanged(selectedPosition);

                        }else if (selectedPosition==position){
                            selectedPosition = -1; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            notifyItemChanged(position);//刷新当前点击item
                        }
                    }
                });

            }
        };
        reCyView.setAdapter(commonAdapter);
    }

    @Override
    public void setupActivityComponent() {

    }


    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        IntentLauncher.with(this).launch(AddressInfoActivity.class);
    }
}
