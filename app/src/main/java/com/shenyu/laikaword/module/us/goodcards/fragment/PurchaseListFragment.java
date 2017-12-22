package com.shenyu.laikaword.module.us.goodcards.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.PurchaseItemAdapter;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
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
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smartRefreshLayout;
    private int type=0;
    int page=1;
    int pagerSize=20;
    private List<PickUpGoodsReponse.PayloadBean> payload=new ArrayList<>();
    @SuppressLint("ValidFragment")
    public PurchaseListFragment(int position) {
        if (position==0){
            this.type=2;
        }
        if (position==1){
            this.type=position;
        }

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
        emptyWrapper.setEmptyView(R.layout.empty_view,"哇哦，篮子暂时是空的～");
        recyclerView.setAdapter(emptyWrapper);

        smartRefreshLayout.setFooterHeight(45);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadmore(true);
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1500);
                requestData();

            }
        });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {
        retrofitUtils.addSubscription(RetrofitUtils.apiStores.newMyExtract(type,page,pagerSize), new ApiCallback<PickUpGoodsReponse>() {
            @Override
            public void onSuccess(PickUpGoodsReponse model) {
                    if (model.isSuccess()&&model.getPayload().size()>0) {
                        for (PickUpGoodsReponse.PayloadBean payloadBean:model.getPayload()){
                            payload.add(payloadBean);
                            page++;
                        }
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
