package com.shenyu.laikaword.module.shop.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.widget.AmountView;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.widget.countdownview.step.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手机卡提货
 */
public class PickUpTelActivity extends LKWordBaseActivity {


    @BindView(R.id.step_view)
    StepView stepView;
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
    EditText tvTihuoTianxia;
    @BindView(R.id.tv_heji)
    TextView tvHeji;
    @BindView(R.id.tv_tihuo_all)
    TextView tvTihuoAll;
    @BindView(R.id.tv_tihuo_commit)
    TextView tvTihuoCommit;

    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up_tel;
    }

    @Override
    public void initView() {
        tvHeji.setText("充值总额:");
        setToolBarTitle("提货申请");
        tvTihuoCommit.setText("确认充值");
    }

    @Override
    public void doBusiness(Context context) {
        List<String> titles = new ArrayList<String>();
        titles.add("提货申请");

        titles.add("提货中");

        titles.add("提货完成");
        stepView.setStepTitles(titles);

    }

    @Override
    public void setupActivityComponent() {

    }


    @OnClick({R.id.tv_tihuo_tianxia, R.id.tv_tihuo_all, R.id.tv_tihuo_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tihuo_tianxia:
                break;
            case R.id.tv_tihuo_all:
                break;
            case R.id.tv_tihuo_commit:
                IntentLauncher.with(this).launch(PickUpSuccessActivity.class);
                break;
        }
    }
}
