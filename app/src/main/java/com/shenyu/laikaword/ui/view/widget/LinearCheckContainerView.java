package com.shenyu.laikaword.ui.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class LinearCheckContainerView extends LinearLayout {
    private int[] mArrays;
    private Context context;

    public LinearCheckContainerView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
        this.context = context;

    }

    public LinearCheckContainerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(int[] arrays){
        this.mArrays = arrays;
        initView();

    }
    private void   initView(){
        this.setOrientation(HORIZONTAL);
        removeAllViews();
        for (int i=0;i<mArrays.length;i++){
            CircleView circleView = new CircleView(context);
            if (i==0&&mArrays[i]%2==0){
                circleView.setBorderColor(Color.BLUE);
            }else if(i==0&&mArrays[i]%2!=0){
                circleView.setBorderColor(Color.GRAY);
            }else if(i==1&&mArrays[i]%2==0){
                circleView.setBorderColor(Color.RED);
            }else if(i==1&&mArrays[i]%2!=0){
                circleView.setBorderColor(Color.GRAY);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(70, 70);
            params.rightMargin=20;
            circleView.setLayoutParams(params);
            addView(circleView);
        }

    }
}
