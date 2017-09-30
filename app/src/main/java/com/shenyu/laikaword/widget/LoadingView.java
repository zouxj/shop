package com.shenyu.laikaword.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.shenyu.laikaword.R;

/**
 * Created by shenyu_zxjCode on 2017/9/30 0030.
 */
public class LoadingView extends LinearLayout implements View.OnClickListener  {

    public static final int NO_DATA = 2;
    public static final int ERROR_DATA =1;
    public static final int NO_NETWORK = 3;
    public static final int GONE = 4;

    TextView imgState;
    TextView tvStateText;
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



    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        init(context);

    }



    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.loading_layout, this);
         imgState = (TextView) mView.findViewById(R.id.img_state);
         tvStateText = mView.findViewById(R.id.tv_state_text);
        setStatue(GONE);

    }



    public void setStatue(int status) {
        setVisibility(View.VISIBLE);
        switch (status){
            case NO_DATA:
                //TODO 没有数据
                break;
            case ERROR_DATA:
                //TODO 服务器异常
                break;
            case NO_NETWORK:
                //TODO 网络异常
                break;

        }

    }



    @Override

    public void onClick(View v) {
        mListener.refresh();
    }
}
