package com.shenyu.laikaword.module.mine.cards;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.SpaceItemDecoration;
import com.shenyu.laikaword.module.mine.cards.fragment.AddBankCardActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 银行卡管理
 */
public class CardBankActivity extends LKWordBaseActivity {

@BindView(R.id.card_cy_list)
    RecyclerView recyclerView;
    @Override
    public int bindLayout() {
        return R.layout.activity_usr_card;
    }

    @Override
    public void initView() {
        setToolBarTitle("我的银行卡");
        setToolBarRight("+");
        mToolbarSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentLauncher.with(CardBankActivity.this).launch(AddBankCardActivity.class);
            }
        });
        List<String> dataList = new ArrayList<>();
        for (int i=0;i<10;i++){
            dataList.add("ad");
        }
        recyclerView.addItemDecoration(new SpaceItemDecoration((int) UIUtil.dp2px(5)),0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonAdapter(R.layout.item_card_list,dataList) {
            private int selectedPosition=-5;
            @Override
            protected void convert(ViewHolder holder, Object o,  int position) {

            }
            @Override
            public void onBindViewHolder(final ViewHolder holder, final int position) {
                if (selectedPosition == position) {
                    holder.setBackground(R.id.item_ck,UIUtil.getDrawable(R.drawable.bg_list_check_rectangle));
                } else {
                    holder.setBackground(R.id.item_ck,UIUtil.getDrawable(R.drawable.bg_list_wite_rectangle));
             }

                holder.setOnClickListener(R.id.item_ck, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition!=position){
                            //先取消上个item的勾选状态
                            if (selectedPosition!=-5) {
                                holder.itemView.setSelected(false);
                                notifyItemChanged(selectedPosition);
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            holder.itemView.setSelected(true);
                            notifyItemChanged(selectedPosition);

                        }else if (selectedPosition==position){
                            selectedPosition = -5; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            notifyItemChanged(position);//刷新当前点击item
                        }
                    }
                });
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
