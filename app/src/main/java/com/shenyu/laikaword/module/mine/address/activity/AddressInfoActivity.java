package com.shenyu.laikaword.module.mine.address.activity;

import android.content.Context;
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
import butterknife.OnClick;

public class AddressInfoActivity extends LKWordBaseActivity {


    @BindView(R.id.rl_address_list)
    RecyclerView rlAddressList;
    private CommonAdapter<String> commonAdapter;


    @Override
    public int bindLayout() {
        return R.layout.activity_add_address_info;
    }

    @Override
    public void initView() {
        setToolBarTitle("我的地址");
        rlAddressList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(9),UIUtil.getColor(R.color.main_bg_gray)));
        rlAddressList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void doBusiness(Context context) {
        final List<String> dataList = new ArrayList<>();
        for (int i=0;i<9;i++){
            dataList.add("item"+i);
        }
        commonAdapter = new CommonAdapter<String>(R.layout.item_mine_address_info,dataList) {
            int selectedPosition=-1;
            @Override
            protected void convert(final ViewHolder holder, String s, final int position) {
                holder.setOnClickListener(R.id.tv_to_edite_address, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IntentLauncher.with(AddressInfoActivity.this).launch(EditAddressActivity.class);
                    }
                });
                CheckBox checkBox = holder.getView(R.id.ck_moren);
                checkBox.setChecked(selectedPosition==position?true:false);
                holder.setOnClickListener(R.id.tv_delete_address, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                checkBox.setOnClickListener(new View.OnClickListener() {
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
        rlAddressList.setAdapter(commonAdapter);
    }

    @Override
    public void setupActivityComponent() {

    }
    @OnClick(R.id.tv_add_address)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_add_address:
                IntentLauncher.with(this).put("Type","ADD").launch(EditAddressActivity.class);
                break;

        }
    }
}
