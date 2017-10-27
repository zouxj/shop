package com.shenyu.laikaword.module.us.goodcards.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.PurchaseItemAdapter;
import com.shenyu.laikaword.model.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.model.bean.reponse.PickUpGoodsReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
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
        retrofitUtils.addSubscription(RetrofitUtils.apiStores.myextract(type), new ApiCallback<PickUpGoodsReponse>() {
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
