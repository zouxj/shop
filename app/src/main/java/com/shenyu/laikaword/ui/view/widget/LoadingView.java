package com.shenyu.laikaword.ui.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.shenyu.laikaword.R;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/30 0030.
 */
public class LoadingView extends LinearLayout implements View.OnClickListener  {

    public static final int NO_DATA = 2;
    public static final int ERROR_DATA =1;
    public static final int NO_NETWORK = 3;
    public static final int GONE = 4;

    private ImageView imgState;
    private TextView tvStateText;
    private View mView;



    private OnRefreshListener mListener;



    public void setRefrechListener(OnRefreshListener mListener) {

        this.mListener = mListener;

    }



    public interface OnRefreshListener {

        void refresh();

    }



    public LoadingView(Context context) {

        super(context);

        init(context);

    }



    public LoadingView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);

    }



    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        init(context);

    }



    @SuppressLint("NewApi")
    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.loading_layout, this);
         imgState = mView.findViewById(R.id.img_state);
         tvStateText = mView.findViewById(R.id.tv_state_text);
         setStatue(View.GONE);
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setStatue(int status) {
        setVisibility(View.VISIBLE);
        switch (status){
            case NO_DATA:
                if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                    imgState.setBackground(UIUtil.getDrawable(R.mipmap.nothing));
                else
                    imgState.setBackgroundResource(R.mipmap.nothing);
                tvStateText.setText("没有数据");
                //TODO 没有数据
                break;
            case ERROR_DATA:
                if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                    imgState.setBackground(UIUtil.getDrawable(R.mipmap.net_error_icon));
                else
                    imgState.setBackgroundResource(R.mipmap.net_error_icon);
                tvStateText.setText("服务器异常");
                //TODO 服务器异常
                break;
            case NO_NETWORK:
                //TODO 网络异常
                if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                    imgState.setBackground(UIUtil.getDrawable(R.mipmap.wrong));
                else
                    imgState.setBackgroundResource(R.mipmap.wrong);
                tvStateText.setText("网络异常");
                break;
            case GONE:
                mView.setVisibility(View.GONE);
                break;

        }

    }



    @Override

    public void onClick(View v) {
        mListener.refresh();
    }
}
