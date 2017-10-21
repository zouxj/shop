package com.shenyu.laikaword.module.mine.cards.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.MultiItemTypeAdapter;
import com.shenyu.laikaword.adapter.PurchaseItemAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.adapter.itemviewdelegeate.PurchaseItemJd;
import com.shenyu.laikaword.adapter.itemviewdelegeate.PurchaseItemLyd;
import com.shenyu.laikaword.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.bean.reponse.PickUpGoodsReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.helper.SpaceItemDecoration;
import com.shenyu.laikaword.module.shop.activity.PickUpStateActivity;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.DateTimeUtil;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class PurchaseListFragment extends IKWordBaseFragment {

    @BindView(R.id.pirchase_ry_view)
    RecyclerView recyclerView;
    private int type=0;
    private List<PickUpGoodsReponse.PayloadBean> payload=new ArrayList<>();
    @SuppressLint("ValidFragment")
    public PurchaseListFragment(int position) {
        this.type=position;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_purchase_list;
    }
    EmptyWrapper emptyWrapper;
    PurchaseItemAdapter adapter;
    @Override
    public void initView(View view) {
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(9),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
         adapter = new PurchaseItemAdapter(payload);
        emptyWrapper =new EmptyWrapper(adapter);
        emptyWrapper.setEmptyView(R.layout.empty_view);
        recyclerView.setAdapter(emptyWrapper);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.myextract(type), new ApiCallback<PickUpGoodsReponse>() {
            @Override
            public void onSuccess(PickUpGoodsReponse model) {
                    if (model.isSuccess()&&null!=model.getPayload()) {
                        payload.clear();
                        payload.addAll(model.getPayload());
                        emptyWrapper.notifyDataSetChanged();
                    }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }


}
