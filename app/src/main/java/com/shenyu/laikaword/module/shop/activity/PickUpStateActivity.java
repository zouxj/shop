package com.shenyu.laikaword.module.shop.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.zxj.utilslibrary.widget.countdownview.step.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提货状态
 */
public class PickUpStateActivity extends LKWordBaseActivity {


    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.iv_tihuo_img)
    ImageView ivTihuoImg;
    @BindView(R.id.tv_tihuo_name)
    TextView tvTihuoName;
    @BindView(R.id.tv_tihuo_count)
    TextView tvTihuoCount;
    @BindView(R.id.tv_name_tel)
    TextView tvNameTel;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_dan_no)
    TextView tvDanNo;
    @BindView(R.id.tv_tihuo_tianxia)
    TextView tvTihuoTianxia;

    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up_state;
    }

    @Override
    public void initView() {
        setToolBarTitle("提货详情");
        List<String> titles = new ArrayList<String>();
        titles.add("提货申请");
        titles.add("提货中");
        titles.add("提货完成");
        stepView.setStepTitles(titles);
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
