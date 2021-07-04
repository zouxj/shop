package com.shenyu.laikaword.ui.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class LinearContainerView extends LinearLayout{
    private int[] mArrays;
    private Context context;

    public LinearContainerView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
        this.context = context;

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public LinearContainerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                circleView.setBorderColor(Color.GRAY);
            circleView.setText(mArrays[i]+"");
            circleView.setTextColor(Color.WHITE);
            circleView.setGravity(Gravity.CENTER);
            if (i==mArrays.length-1){
                circleView.setBorderColor(Color.YELLOW);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(70, 70);
            params.rightMargin=20;
            circleView.setLayoutParams(params);
            addView(circleView);
        }

}
}
