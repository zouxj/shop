package com.shenyu.laikaword.module.us.resell.ui.activity;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CDkeyActivity extends LKWordBaseActivity {

@BindView(R.id.rv_cdkey)
    RecyclerView recyclerView;
    private List<String> cdKey= new ArrayList<>();
    CommonAdapter commonAdapter;
    @Override
    public int bindLayout() {
        return R.layout.activity_cdkey;
    }

    @Override
    public void initView() {
        setToolBarTitle("兑换码");
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(1),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commonAdapter=   new CommonAdapter<String>(R.layout.item_cdkey,cdKey) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.tv_cdkey,"兑换码:"+o);
            }

        };
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void doBusiness(Context context) {
        if (getIntent().getStringArrayListExtra("cdkey")!=null){
            cdKey.clear();
            cdKey.addAll(getIntent().getStringArrayListExtra("cdkey"));
            commonAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setupActivityComponent() {

    }
}
