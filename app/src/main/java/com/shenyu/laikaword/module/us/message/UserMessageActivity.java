package com.shenyu.laikaword.module.us.message;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.adapter.ViewHolder;
import com.shenyu.laikaword.model.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.MessageReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserMessageActivity extends LKWordBaseActivity {


    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    List<MessageReponse.PayloadBean> payload;
    CommonAdapter commonAdapter;
    EmptyWrapper emptyWrapper;

    @Override
    public int bindLayout() {
        return R.layout.activity_user_message;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView() {
        setToolBarTitle("通知消息");
        rvItem.setLayoutManager(new LinearLayoutManager(this));
        rvItem.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL, 2,UIUtil.getColor(R.color.divider)));
    }

    @Override
    public void doBusiness(Context context) {
        payload = new ArrayList<>();
        commonAdapter = new CommonAdapter<MessageReponse.PayloadBean>(R.layout.item_message_layout,payload) {
            @Override
            protected void convert(ViewHolder holder, MessageReponse.PayloadBean o, int position) {
                holder.setText(R.id.message_content,o.getContent());
            }


        };
         emptyWrapper = new EmptyWrapper(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view);
        rvItem.setAdapter(emptyWrapper);
        loadData();
    }

    /**
     * 获取消息数据
     */
    private void loadData() {
        loadViewHelper.showLoadingDialog(this);
        retrofitUtils.addSubscription(RetrofitUtils.apiStores.messageList(), new ApiCallback<MessageReponse>() {
            @Override
            public void onSuccess(MessageReponse model) {
                if (model.isSuccess()) {
                    payload.clear();
                    payload.addAll(model.getPayload());
                    emptyWrapper.notifyDataSetChanged();
                }else {
                    ToastUtil.showToastShort(model.getError().getMessage());
                }
                loadViewHelper.closeLoadingDialog();

            }

            @Override
            public void onFailure(String msg) {
                layoutContent.setVisibility(View.GONE);
                loadViewHelper.showErrorResert(UserMessageActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadData();
                        layoutContent.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onFinish() {
                loadViewHelper.closeLoadingDialog();
            }
        });
    }

    @Override
    public void setupActivityComponent() {

    }

}
