package com.shenyu.laikaword.module.mine.cards.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.SpaceItemOneDecoration;
import com.zxj.utilslibrary.utils.IntentLauncher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 银行卡管理
 */
public class CardBankActivity extends LKWordBaseActivity {

    @BindView(R.id.card_cy_list)
    RecyclerView recyclerView;
    private String bankName;
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
        recyclerView.addItemDecoration(new SpaceItemOneDecoration(R.color.divider,1,LinearLayoutManager.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonAdapter<String>(R.layout.item_card_list,dataList) {
            private int selectedPosition=-5;
            @Override
            protected void convert(final ViewHolder holder, final String str, final int position) {
                CheckBox checkBox = holder.getView(R.id.cb_bank_name);
                checkBox.setChecked(selectedPosition==position?true:false);
                holder.setOnClickListener(R.id.item_ck, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition!=position){
                            //先取消上个item的勾选状态
                            if (selectedPosition!=-5) {
                                holder.itemView.setSelected(false);
                                notifyItemChanged(selectedPosition);
                                bankName = null;
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            holder.itemView.setSelected(true);
                            bankName = str;
                            notifyItemChanged(selectedPosition);

                        }else if (selectedPosition==position){
                            selectedPosition = -5; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            bankName = null;
                            notifyItemChanged(position);//刷新当前点击item
                        }
                    }
                });
            }
        });
    }
    @OnClick(R.id.tv_commit_selector)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_commit_selector:
                Intent intent =getIntent();
                Bundle  bundle =new Bundle();
                bundle.putCharSequence("bankName",bankName);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    public void doBusiness(Context context) {


    }

    @Override
    public void setupActivityComponent() {

    }
}
