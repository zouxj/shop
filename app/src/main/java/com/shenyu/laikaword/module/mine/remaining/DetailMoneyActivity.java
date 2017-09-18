package com.shenyu.laikaword.module.mine.remaining;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 余额明细
 */
public class DetailMoneyActivity extends LKWordBaseActivity {

    @BindView(R.id.rl_view)
    RecyclerView recyclerView;
    @Override
    public int bindLayout() {
        return R.layout.activity_detail_money;
    }

    @Override
    public void initView() {
        setToolBarTitle("余额明细");
        List<String> datil=new ArrayList<>();
        for (int i=0;i<10;i++){
            datil.add("item");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonAdapter(R.layout.item_daile_money,datil) {
            @Override
            protected void convert(ViewHolder holder, Object o, int position) {

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
