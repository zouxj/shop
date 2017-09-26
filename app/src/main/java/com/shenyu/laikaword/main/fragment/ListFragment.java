package com.shenyu.laikaword.main.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.helper.MainItemSpaceItemDecoration;
import com.shenyu.laikaword.module.shop.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.rxbus.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends IKWordBaseFragment {


    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    CommonAdapter commonAdapter;
    @Override
    public int bindLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void doBusiness() {

        final List<String> list = new ArrayList<>();
        for (int i=0;i<60;i++){
            list.add("item"+i);
        }
        MainItemSpaceItemDecoration spaceItemDecoration = new MainItemSpaceItemDecoration((int) UIUtil.dp2px(8));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.addItemDecoration(spaceItemDecoration);
        commonAdapter=new CommonAdapter<String>(R.layout.item_home_shop,list) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                Picasso.with(UIUtil.getContext()).load("url").placeholder(R.mipmap.yidong_icon)
                 .resize(50, 50)
                   .centerCrop().into((ImageView) holder.getView(R.id.iv_main_shop_img));
                holder.setText(R.id.tv_main_shop_name, "100元移动充值卡");
                holder.setText(R.id.tv_main_shop_original_price, "￥100");
                holder.setText(R.id.tv_main_shop_price, "￥94");
                holder.setText(R.id.tv_main_shop_surplus, "还剩十张");
                holder.setText(R.id.tv_main_shop_seller,"带刺的玫瑰 出售");
                holder.setText(R.id.tv_mian_shop_discount,"9.3折");
                holder.setOnClickListener(R.id.tv_main_shop_purchase, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      IntentLauncher.with(getActivity()).launch(ConfirmOrderActivity.class);
                    }
                });
                holder.setOnClickListener(R.id.lv_main_shop, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtil.showToastShort("进入详情");
                    }
                });


            }
        };
        recycleView.setAdapter(commonAdapter);
        RxBus.getDefault().toObservable(EventType.class).subscribe(new Action1<EventType>() {
            @Override
            public void call(EventType eventType) {
                switch (eventType.action){
                    case EventType.ACTION_LODE_MORE://上拉加载更多
                        list.addAll((List)eventType.object);
                        break;
                    case EventType.ACTION_PULL_REFRESH://下拉刷新
                        list.clear();
                        list.addAll((List)eventType.object);
                        break;
                }
                commonAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {

    }

}
