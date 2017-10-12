package com.shenyu.laikaword.module.mine.address.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.reponse.AddressReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.shenyu.laikaword.rxbus.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class AddressInfoActivity extends LKWordBaseActivity {

    @BindView(R.id.rl_address_list)
    RecyclerView rlAddressList;
    private CommonAdapter<AddressReponse.PayloadBean> commonAdapter;
    private List<AddressReponse.PayloadBean> payload;

    @Override
    public int bindLayout() {
        return R.layout.activity_add_address_info;
    }

    @Override
    public void initView() {
        setToolBarTitle("我的地址");
        rlAddressList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(9),UIUtil.getColor(R.color.main_bg_gray)));
        rlAddressList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void doBusiness(Context context) {
        payload = new ArrayList<>();
        RxBus.getDefault().toObservable(EventType.class).subscribe(new Action1<EventType>() {
            @Override
            public void call(EventType eventType) {
                switch (eventType.action) {
                    case EventType.ACTION_UPDATA_USER_ADDRESS:
                        initData();
                        break;
                }
            }
        });
        initData();
        commonAdapter = new CommonAdapter<AddressReponse.PayloadBean>(R.layout.item_mine_address_info,payload) {
            int selectedPosition=-1;
            @Override
            protected void convert(final ViewHolder holder, final AddressReponse.PayloadBean addressReponse, final int position) {
                holder.setText(R.id.tv_address_name,addressReponse.getReceiveName());
                holder.setText(R.id.tv_address_tel,addressReponse.getPhone());
                holder.setOnClickListener(R.id.tv_to_edite_address, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        IntentLauncher.with(AddressInfoActivity.this).put("AddressInfo",addressReponse).launch(EditAddressActivity.class);
                    }
                });
                CheckBox checkBox = holder.getView(R.id.ck_moren);
                checkBox.setChecked(selectedPosition==position?true:false||addressReponse.getDefaultX()==1);
                holder.setOnClickListener(R.id.tv_delete_address, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.deleteAddress(addressReponse.getAddressId()), new ApiCallback<BaseReponse>() {
                            @Override
                            public void onSuccess(BaseReponse model) {
                                if (model.isSuccess()){
                                    payload.remove(position);
                                    notifyDataSetChanged();
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
                });
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition!=position){
                            //先取消上个item的勾选状态
                            if (selectedPosition!=-1) {
                                holder.itemView.setSelected(false);
                                notifyItemChanged(selectedPosition);
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            holder.itemView.setSelected(true);
                            notifyItemChanged(selectedPosition);

                        }else if (selectedPosition==position){
                            selectedPosition = -1; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            notifyItemChanged(position);//刷新当前点击item
                        }
                    }
                });
            }
        };
        rlAddressList.setAdapter(commonAdapter);
    }

    protected void initData() {
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.getAddress(), new ApiCallback<AddressReponse>() {
            @Override
            public void onSuccess(AddressReponse model) {
                if (model.isSuccess());{
                    payload.clear();
                    payload.addAll(model.getPayload());
                    commonAdapter.notifyDataSetChanged();
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

    @Override
    public void setupActivityComponent() {

    }
    @OnClick(R.id.tv_add_address)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_add_address:
                IntentLauncher.with(this).put("Type","ADD").launch(EditAddressActivity.class);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
