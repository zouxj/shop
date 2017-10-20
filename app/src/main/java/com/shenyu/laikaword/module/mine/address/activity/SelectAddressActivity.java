package com.shenyu.laikaword.module.mine.address.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.adapter.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.reponse.AddressReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectAddressActivity extends LKWordBaseActivity {


    @BindView(R.id.re_cy_view)
    RecyclerView reCyView;
    private List<AddressReponse.PayloadBean> payload=new ArrayList<>();
    @Override
    public int bindLayout() {
        return R.layout.activity_select_address;
    }
    EmptyWrapper emptyWrapper;
    @Override
    public void initView() {
        setToolBarTitle("选择地址");
        setToolBarRight("管理",0);
        reCyView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, (int) UIUtil.dp2px(5),UIUtil.getColor(R.color.main_bg_gray)));
        reCyView.setLayoutManager(new LinearLayoutManager(this));
    }
    CommonAdapter<AddressReponse.PayloadBean> commonAdapter;
    @Override
    public void doBusiness(Context context) {
        final Intent intent =getIntent();
        final Bundle  bundle =new Bundle();
          commonAdapter=   new CommonAdapter<AddressReponse.PayloadBean>(R.layout.item_select_address,payload) {
            int selectedPosition=-1;
            @Override
            protected void convert(final ViewHolder holder, final AddressReponse.PayloadBean payloadBean, final int position) {
                CheckBox checkBox = holder.getView(R.id.ck_select_address);
                    holder.setText(R.id.tv_select_address, payloadBean.getProvince()+payloadBean.getCity()+payloadBean.getDetail());
                    holder.setText(R.id.tv_select_phone, payloadBean.getPhone());
                    holder.setText(R.id.tv_select_name, payloadBean.getReceiveName());
                checkBox.setChecked(selectedPosition == position ? true : false || payloadBean.getDefaultX() == 1);
                bundle.putSerializable("address",payloadBean);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                checkBox.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition!=position){
                            //先取消上个item的勾选状态
                            if (selectedPosition!=-1) {
                                holder.itemView.setSelected(false);
                                bundle.clear();
                                emptyWrapper.notifyItemChanged(selectedPosition);
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            holder.itemView.setSelected(true);
                            emptyWrapper.notifyItemChanged(selectedPosition);
                            bundle.putSerializable("address",payloadBean);
                            intent.putExtras(bundle);
                            setResult(RESULT_OK,intent);

                        }else if (selectedPosition==position){
                            selectedPosition = -1; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            bundle.clear();
                            emptyWrapper.notifyItemChanged(position);//刷新当前点击item
                        }


                    }
                });

            }
        };
         emptyWrapper = new EmptyWrapper<AddressReponse.PayloadBean>(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view);
        reCyView.setAdapter(emptyWrapper);
        initData();

    }

    @Override
    public void setupActivityComponent() {

    }


    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        IntentLauncher.with(this).launch(AddressInfoActivity.class);
    }

    public void initData(){
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
}
