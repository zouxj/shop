package com.shenyu.laikaword.module.us.wallet;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.MoneyDetailReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.zxj.utilslibrary.utils.DateTimeUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 余额明细
 */
public class DetailMoneyActivity extends LKWordBaseActivity {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rl_view)
    RecyclerView recyclerView;
    EmptyWrapper emptyWrapper;
    int page=1;
    int pageSize=20;
    private List<MoneyDetailReponse.PayloadBean> payload=new ArrayList<>();
    @Override
    public int bindLayout() {
        return R.layout.activity_detail_money;
    }

    @Override
    public void initView() {
        setToolBarTitle("余额明细");
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadmore(true);
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1500);
                if (requestData(++page,pageSize))
                    ToastUtil.showToastShort("没有更多的数据了!");
            }
        });
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,2,UIUtil.getColor(R.color.main_bg_gray) ));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         emptyWrapper = new EmptyWrapper(new CommonAdapter<MoneyDetailReponse.PayloadBean>(R.layout.item_daile_money,payload) {
            @Override
            protected void convert(ViewHolder holder, MoneyDetailReponse.PayloadBean payloadBean, int position) {
                holder.setText(R.id.tv_chongzhi_type,payloadBean.getDetail());
                holder.setText(R.id.tv_mongey_time, DateTimeUtil.formatDate(Long.valueOf(payloadBean.getCreateTime()),"yyyy-MM-dd HH:mm:ss"));
                holder.setText(R.id.tv_money_count,payloadBean.getMoney());
            }

        });
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.money_empty));
        recyclerView.setAdapter(emptyWrapper);
    }

    @Override
    public void doBusiness(Context context) {
        loadViewHelper.showLoadingDialog(mActivity);
        requestData(page,pageSize);

    }

    @Override
    public void setupActivityComponent() {

    }
    boolean  bool = false;
    private boolean requestData(final int page, int sizePage){
       retrofitUtils.addSubscription(RetrofitUtils.apiStores.getUserMoneyDetail(page,sizePage), new ApiCallback<MoneyDetailReponse>() {

            @Override
            public void onSuccess(MoneyDetailReponse model) {
                if (model.isSuccess()&&model.getPayload().size()>0){
                    for (MoneyDetailReponse.PayloadBean payloadBean:model.getPayload()) {
                        payload.add(payloadBean);
                    }
                    emptyWrapper.notifyDataSetChanged();
                    bool = false;

                }else {
                    bool = true;
                }
                loadViewHelper.closeLoadingDialog();
            }

            @Override
            public void onFailure(String msg) {
                loadViewHelper.closeLoadingDialog();
                bool = true;
            }

            @Override
            public void onFinish() {
                loadViewHelper.closeLoadingDialog();
            }
        });
        return bool;
    }
}
