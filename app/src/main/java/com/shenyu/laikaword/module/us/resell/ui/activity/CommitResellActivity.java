package com.shenyu.laikaword.module.us.resell.ui.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.mine.MineModule;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.GoodsViewGroupItem;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.ui.view.widget.AmountView;
import com.shenyu.laikaword.ui.view.widget.GoodsViewGroup;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CommitResellActivity extends LKWordBaseActivity {

    @BindView(R.id.goods_viewgroup)
    GoodsViewGroup goodsViewGroup;
    @BindView(R.id.ry_lv)
    RecyclerView recyclerView;
    @BindView(R.id.av_zj)
    AmountView amountView;

    @Override
    public int bindLayout() {
        return R.layout.activity_commit_resell;
    }

    @Override
    public void initView() {
        amountView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        amountView.setGoods_storage(10);
        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
             ToastUtil.showToastShort(amount+"test");
            }
        });
        goodsViewGroup.addItemViews(getItems());
        goodsViewGroup.setGroupClickListener(new GoodsViewGroup.OnGroupItemClickListener() {
            @Override
            public void onGroupItemClick(int itemPos, String key, String value) {
                ToastUtil.showToastShort("key=>"+key+"==value"+value);
            }
        });
        List<String> dataList = new ArrayList<>();
        for (int i=0;i<2;i++){
            dataList.add("item");
        }
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(1),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new CommonAdapter(R.layout.resell_goods_item,dataList) {
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
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this)).inject(this);
    }
    private List<GoodsViewGroupItem> getItems() {
        List<GoodsViewGroupItem> items = new ArrayList<>();
        items.add(new GoodsViewGroupItem(0 + "", "9.2折\n最快"));
        items.add(new GoodsViewGroupItem(1 + "", "9.5折\n标准"));
        items.add(new GoodsViewGroupItem(2 + "", "9.7折\n收益高"));
        return items;
    }

}
