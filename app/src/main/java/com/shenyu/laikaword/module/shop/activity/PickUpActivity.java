package com.shenyu.laikaword.module.shop.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.reponse.AddressReponse;
import com.shenyu.laikaword.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.module.mine.address.activity.SelectAddressActivity;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.shenyu.laikaword.widget.AmountView;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import com.zxj.utilslibrary.widget.countdownview.step.StepView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PickUpActivity extends LKWordBaseActivity {

    @BindView(R.id.step_view)
    StepView setStepTitles;
    @BindView(R.id.iv_tihuo_img)
    ImageView ivTihuoImg;
    @BindView(R.id.tv_tihuo_name)
    TextView tvTihuoName;
    @BindView(R.id.tv_tihuo_count)
    TextView tvTihuoCount;
    @BindView(R.id.tv_goumai_count)
    TextView tvGoumaiCount;
    @BindView(R.id.tv_tihuo_all_select)
    TextView tvTihuoAllSelect;
    @BindView(R.id.av_zj)
    AmountView avZj;
    @BindView(R.id.tv_tihuo_tianxia)
    TextView tvTihuoTianxia;
    @BindView(R.id.tv_heji)
    TextView tvHeji;
    @BindView(R.id.tv_tihuo_all)
    TextView tvTihuoAll;
    @BindView(R.id.tv_tihuo_commit)
    TextView tvTihuoCommit;
    private int count=1;
    CarPagerReponse.Bean bean;
    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up;
    }

    @Override
    public void initView() {
        setToolBarTitle("申请提货");
        avZj.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                count = amount;
            }
        });
    }

    @Override
    public void doBusiness(Context context) {
        List<String> titles = new ArrayList<String>();

        titles.add("提货申请");

        titles.add("提货中");

        titles.add("提货完成");

        setStepTitles.setStepTitles(titles);
         bean = (CarPagerReponse.Bean) getIntent().getSerializableExtra("bean");
        if (null!=bean){
            Picasso.with(UIUtil.getContext()).load(bean.getGoodsImage()).placeholder(R.mipmap.yidong_icon)
                    .error(R.mipmap.yidong_icon).into(ivTihuoImg);
            tvTihuoName.setText(bean.getGoodsName());
            if (StringUtil.validText(bean.getQuantity())) {
                tvTihuoCount.setText("数量:" + bean.getQuantity());
                avZj.setGoods_storage(Integer.parseInt(bean.getQuantity()));
            }
        }
    }


    @Override
    public void setupActivityComponent() {

    }

    @OnClick({R.id.tv_tihuo_tianxia, R.id.tv_tihuo_all, R.id.tv_tihuo_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tihuo_tianxia:
                //TODO 去选择收货地址
                Intent intent = new Intent(PickUpActivity.this,SelectAddressActivity.class);
                startActivityForResult(intent,Constants.REQUEST_ADDRESS);
                break;
            case R.id.tv_tihuo_all_select:
                if (null!=bean)
                    avZj.etAmount.setText(bean.getQuantity());
                break;
            case R.id.tv_tihuo_commit:
                if (StringUtil.validText(tvTihuoTianxia.getText().toString().trim())) {
                    Map<String,String> param = new HashMap<>();
//                    param.put("packageId",);
//                    requestData();

                } else{
                DialogHelper.tianXAddress(this, new DialogHelper.ButtonCallback() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        Intent intent = new Intent(PickUpActivity.this,SelectAddressActivity.class);
                        startActivityForResult(intent,Constants.REQUEST_ADDRESS);

                    }

                    @Override
                    public void onPositive(Dialog dialog) {

                    }
                });
                }
                //TODO 确认提货
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //判断是哪一个的回调
            if (requestCode == Constants.REQUEST_ADDRESS) {
             AddressReponse.PayloadBean payloadBean   = (AddressReponse.PayloadBean) data.getSerializableExtra("address");
        if (null!=payloadBean){
            Spanned dizhi= Html.fromHtml("<font color='#333333' >"+payloadBean.getReceiveName()+payloadBean.getPhone()+"</font><br/><font color='#999999' >"+payloadBean.getProvince()+payloadBean.getCity()+payloadBean.getDetail()+"</font>");
            tvTihuoTianxia.setGravity(Gravity.CENTER);
            tvTihuoTianxia.setText(dizhi);
        }
            }
        }
    }

    public void requestData(Map<String,String> param){
        RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.extractOrder(param), new ApiCallback() {
            @Override
            public void onSuccess(Object model) {
                IntentLauncher.with(PickUpActivity.this).launch(PickUpSuccessActivity.class);
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
