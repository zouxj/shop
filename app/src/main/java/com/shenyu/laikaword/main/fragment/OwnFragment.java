package com.shenyu.laikaword.main.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.widget.IdentityImageView;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import com.zxj.utilslibrary.utils.ViewUtils;
import com.zxj.utilslibrary.widget.countdownview.CountdownView;
import com.zxj.utilslibrary.widget.countdownview.DynamicConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnFragment extends IKWordBaseFragment {
    @BindView(R.id.use_id)
    TextView useId;
    @BindView(R.id.cv_countdownView)
    CountdownView cvCountdownView;
    Unbinder unbinder;
    @BindView(R.id.cv_code)
    CountdownView cvCode;
    @BindView(R.id.iiv)
    IdentityImageView identityImageView;

    @Override
    public int bindLayout() {
        return R.layout.fragment_own;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void doBusiness() {
//        iconNmuberView.setIcon(R.mipmap.ic_launcher);
        identityImageView.getBigCircleImageView().setImageResource(R.mipmap.xxxx);
//        identityImageView.setBorderWidth(5);
//        identityImageView.setBorderColor(R.color.white);
//        identityImageView.setIsprogress(true);
//        identityImageView.setProgressColor(R.color.gold);
//        identityImageView.getSmallCircleImageView().setImageResource(R.mipmap.v);
        refreshTime(5000);
        cvCode.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onEnd(CountdownView cv) {
//                dynamicConfigBuilderCode.setShowSecond(false);
                dynamicConfigBuilderCode.setSuffixSecond("重新发送");
//                ToastUtil.showToastShort("xxxx刚刚抢到了1000元充值卡", Gravity.LEFT);
                cvCode.setEnabled(true);
                cvCode.dynamicShow(dynamicConfigBuilderCode.build());

            }
        });
        cvCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                cvCode.setEnabled(false);
                dynamicConfigBuilderCode.setSuffixSecond("");
                cvCode.dynamicShow(dynamicConfigBuilderCode.build());
                cvCode.start(5000);
                ViewUtils.setPwdDialog(getActivity(),null,null,null,null,false,null).show();
//                ToastUtil.showToastShort("重新發送");
                return false;
            }
        });


    }

    @Override
    public void setupFragmentComponent() {

    }

    /**
     * 刷新时间
     *
     * @param leftTime
     */
    DynamicConfig.Builder dynamicConfigBuilderCode;
    public void refreshTime(long leftTime) {
        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
        DynamicConfig.BackgroundInfo backgroundInfo = new DynamicConfig.BackgroundInfo();
        backgroundInfo.setColor(0xFFFF54BC).setSize(30f).setRadius(0f).setShowTimeBgDivisionLine(false);
        dynamicConfigBuilder.setTimeTextSize(15)
        .setTimeTextColor(0xFFFFFFFF)
                .setTimeTextBold(true)
                .setSuffixTextColor(0xFF000000)
                .setSuffixTextSize(15)
                .setBackgroundInfo(backgroundInfo)
                .setShowDay(false).setShowHour(false).setShowMinute(true).setShowSecond(true).setShowMillisecond(true);
        cvCountdownView.dynamicShow(dynamicConfigBuilder.build());

        dynamicConfigBuilderCode = new DynamicConfig.Builder();
        DynamicConfig.BackgroundInfo backgroundInfoCode = new DynamicConfig.BackgroundInfo();
        backgroundInfoCode.setColor(0xFFFF54BC)
                .setSize(30f)
                .setRadius(0f)
                .setShowTimeBgDivisionLine(false);
        dynamicConfigBuilderCode.setTimeTextSize(15)
                .setTimeTextColor(0xFFFFFFFF)
                .setTimeTextBold(true)
                .setSuffixTextColor(0xFF000000)
                .setSuffixTextSize(15)
                .setBackgroundInfo(backgroundInfoCode).setShowSecond(true);
        cvCountdownView.dynamicShow(dynamicConfigBuilder.build());
        cvCode.dynamicShow(dynamicConfigBuilderCode.build());
        if (leftTime > 0) {
            cvCountdownView.start(leftTime);
        } else {
            cvCountdownView.stop();
            cvCountdownView.allShowZero();
        }
        if (leftTime > 0) {
            cvCode.start(leftTime);
        } else {
            cvCode.stop();
            cvCode.allShowZero();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
//    public void testOn(View view){
//        ViewUtils.setPwdDialog(getActivity(),null,null,null,null,false,null);
//    }
}
