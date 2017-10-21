package com.shenyu.laikaword.module.mine.address.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.reponse.AddressReponse;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.shenyu.laikaword.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.rxbus.RxSubscriptions;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class AddressInfoActivity extends LKWordBaseActivity {

    @BindView(R.id.rl_address_list)
    RecyclerView rlAddressList;
    private CommonAdapter<AddressReponse.PayloadBean> commonAdapter;
    private List<AddressReponse.PayloadBean> payload;
    EmptyWrapper<AddressReponse.PayloadBean> emptyWrapper;
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
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_UPDATA_USER_ADDRESS:
                                initData();
                                break;
                        }
                        LogUtil.e(TAG, myEvent.event+"____"+"threadType=>"+Thread.currentThread());
//            }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e(TAG, "onError");
                        /**
                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
                         */
                        subscribeEvent();
                    }
                });
        RxSubscriptions.add(mRxSub);
    }
    private  boolean bools = true;
    @Override
    public void doBusiness(Context context) {
        payload = new ArrayList<>();
        initData();
        subscribeEvent();
        commonAdapter = new CommonAdapter<AddressReponse.PayloadBean>(R.layout.item_mine_address_info,payload) {
            int selectedPosition=-1;
            @Override
            protected void convert(final ViewHolder holder, final AddressReponse.PayloadBean addressReponse, final int position) {
                holder.setText(R.id.tv_address_name,addressReponse.getReceiveName());
                holder.setText(R.id.tv_address_tel,addressReponse.getPhone());
                holder.setText(R.id.tv_select_adress, addressReponse.getProvince()+addressReponse.getCity()+addressReponse.getDetail());
                holder.setOnClickListener(R.id.tv_to_edite_address, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IntentLauncher.with(AddressInfoActivity.this).put("AddressInfo",addressReponse).launch(EditAddressActivity.class);
                    }
                });

                CheckBox checkBox = holder.getView(R.id.ck_moren);
                if (addressReponse.getDefaultX()==1){
                    selectedPosition=position;
                }else {
                    selectedPosition=-1;
                }
                checkBox.setChecked((selectedPosition==position?true:false&&addressReponse.getDefaultX()==1));
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
                                bools=false;
                                emptyWrapper.notifyItemChanged(selectedPosition);
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            holder.itemView.setSelected(true);
                            request(addressReponse,1);
                            bools=true;
                            emptyWrapper.notifyItemChanged(selectedPosition);

                        }else if (selectedPosition==position){
                            selectedPosition = -1; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            bools=false;
                            emptyWrapper.notifyItemChanged(position);//刷新当前点击item
                        }
                    }
                });
            }
        };
         emptyWrapper = new EmptyWrapper<AddressReponse.PayloadBean>(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view);
        rlAddressList.setAdapter(emptyWrapper);
    }

    protected void initData() {
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.getAddress(), new ApiCallback<AddressReponse>() {
            @Override
            public void onSuccess(AddressReponse model) {
                if (model.isSuccess());{
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

    public void request(AddressReponse.PayloadBean payloadBean,int def){
        Map<String,String> mapParam = new HashMap<>();
        mapParam.put("addressId",payloadBean.getAddressId());
        mapParam.put("phone",payloadBean.getPhone());
        mapParam.put("receiveName",payloadBean.getReceiveName());
        mapParam.put("detail",payloadBean.getDetail());
        mapParam.put("default",def+"");
        mapParam.put("province",payloadBean.getProvince());
        mapParam.put("city",payloadBean.getCity());
        mapParam.put("district",payloadBean.getDistrict());
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.setAddress(mapParam), new ApiCallback<BaseReponse>() {
            @Override
            public void onSuccess(BaseReponse model) {
                if (model.isSuccess()) {
                    ToastUtil.showToastShort("设置默认成功");
                    RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_ADDRESS,null));
                }
                else {

                }
            }

            @Override
            public void onFailure(String msg) {
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onStarts() {
                super.onStarts();
            }
        });

    }
}
