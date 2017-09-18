package com.zxj.utilslibrary.widget.countdownview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.zxj.utilslibrary.R;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class VerificationCodeView extends CountdownView {
    public VerificationCodeView(Context context) {
        this(context, null);
    }

    public VerificationCodeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerificationCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void start(long millisecond) {
        if (millisecond <= 0) return;
        mPreviousIntervalCallbackTime = 0;
        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.stop();
            mCustomCountDownTimer = null;
        }

        long countDownInterval;
        if (mCountdown.isShowMillisecond) {
            countDownInterval = 10;
            updateShow(millisecond);
        } else {
            countDownInterval = 1000;
        }

        mCustomCountDownTimer = new CustomCountDownTimer(millisecond, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateShow(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // countdown end
                mCountdown.setSuffix( UIUtil.getString(R.string.code_mobile));
//                invalidate();
                allShowZero();
                // callback
                setEnabled(true);


            }
        };
        mCustomCountDownTimer.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
        setEnabled(false);
        mCountdown.mSuffixSecond="";
        invalidate();
    }
}
