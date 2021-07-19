package com.shenyu.laikaword.model.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.holder.ViewHolder;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/12/14 0014.
 */

public class HomeAdapter extends CommonAdapter<String> {
    public HomeAdapter(int layoutId, List<String> datas) {
        super(layoutId, datas);
    }


    @Override
    protected void convert(ViewHolder viewHolder, String payloadBean, int position) {
        ImageView bgImage = viewHolder.getView(R.id.iv_goumai_img);
        ViewGroup.LayoutParams layoutParams = bgImage.getLayoutParams();
        layoutParams.height = Integer.parseInt(payloadBean);
        bgImage.setLayoutParams(layoutParams);

    }
}