package com.shenyu.laikaword.module.shop.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.ViewUtils;
import com.shenyu.laikaword.module.mine.address.activity.AddressInfoActivity;
import com.shenyu.laikaword.module.mine.address.activity.SelectAddressActivity;
import com.shenyu.laikaword.widget.AmountView;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.widget.countdownview.step.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private int count;

    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up;
    }

    @Override
    public void initView() {
        setToolBarTitle("申请提货");
        avZj.setGoods_storage(50);
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
    }

    public void next(View view) {
        setStepTitles.nextStep();

    }

    public void reset(View view) {
        setStepTitles.reset();

    }

    @Override
    public void setupActivityComponent() {

    }

    @OnClick({R.id.tv_tihuo_tianxia, R.id.tv_tihuo_all, R.id.tv_tihuo_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tihuo_tianxia:
                //TODO 去选择收货地址


                break;
            case R.id.tv_tihuo_all_select:
                //TODO 将商品总共的数量全部提货
                break;
            case R.id.tv_tihuo_commit:
                if (StringUtil.validText(tvTihuoTianxia.getText().toString().trim()))
                    IntentLauncher.with(this).launch(PickUpSuccessActivity.class);
                else
                ViewUtils.tianXAddress(this, new ViewUtils.ButtonCallback() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        IntentLauncher.with(PickUpActivity.this).launch(SelectAddressActivity.class);
                    }

                    @Override
                    public void onPositive(Dialog dialog) {

                    }
                });

                //TODO 确认提货
                break;
        }
    }
}
