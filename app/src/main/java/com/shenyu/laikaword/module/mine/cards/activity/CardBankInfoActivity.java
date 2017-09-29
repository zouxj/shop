package com.shenyu.laikaword.module.mine.cards.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.UIUtil;
import com.shenyu.laikaword.helper.DialogHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CardBankInfoActivity extends LKWordBaseActivity {

    @BindView(R.id.card_cy_list)
    RecyclerView recyclerView;
    CommonAdapter<String> commonAdapter;
    List<String> dataBank=new ArrayList<>();
    @Override
    public int bindLayout() {
        return R.layout.activity_card_bank_info;
    }

    @Override
    public void initView() {
        setToolBarTitle("银行卡");
        setToolBarRight("+");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,1,UIUtil.getColor(R.color.main_bg_gray)));
        commonAdapter = new CommonAdapter<String>(R.layout.item_cardinfo_list,dataBank) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                holder.setOnClickListener(R.id.tv_card_num, new  View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogHelper.deleteBankDialog(CardBankInfoActivity.this, new DialogHelper.ButtonCallback() {
                            @Override
                            public void onNegative(Dialog dialog) {
                                dataBank.remove(position);
                                commonAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onPositive(Dialog dialog) {

                            }
                        });
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void doBusiness(Context context) {
        for (int i=0;i<10;i++){
            dataBank.add("item"+i);
        }
        commonAdapter.notifyDataSetChanged();


    }
    @OnClick(R.id.toolbar_subtitle)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_subtitle:
                IntentLauncher.with(this).launch(AddBankCardActivity.class);
                break;
        }
}
    @Override
    public void setupActivityComponent() {

    }
}
