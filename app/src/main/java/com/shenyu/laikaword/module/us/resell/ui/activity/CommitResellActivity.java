package com.shenyu.laikaword.module.us.resell.ui.activity;

import android.content.Context;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.mine.MineModule;
import com.shenyu.laikaword.module.launch.LaiKaApplication;

import java.util.ArrayList;
import java.util.List;

public class CommitResellActivity extends LKWordBaseActivity {

//    @BindView(R.id.goods_viewgroup)
//    GoodsViewGroup goodsViewGroup;

    @Override
    public int bindLayout() {
        return R.layout.activity_commit_resell;
    }

    @Override
    public void initView() {
//        goodsViewGroup.addItemViews(getItems());
//        goodsViewGroup.setGroupClickListener(new GoodsViewGroup.OnGroupItemClickListener() {
//            @Override
//            public void onGroupItemClick(int itemPos, String key, String value) {
//                ToastUtil.showToastShort("key=>"+key+"==value"+value);
//            }
//        });
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this)).inject(this);
    }
//    private List<GoodsViewGroupItem> getItems() {
//        List<GoodsViewGroupItem> items = new ArrayList<>();
//        items.add(new GoodsViewGroupItem(0 + "", "9.2折\n最快"));
//        items.add(new GoodsViewGroupItem(1 + "", "9.5折\n标准"));
//        items.add(new GoodsViewGroupItem(2 + "", "9.7折\n收益高"));
//        return items;
//    }

}
