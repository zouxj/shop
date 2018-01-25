package com.shenyu.laikaword.model.itemviewdelegeate;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.holder.ViewHolder;

public class ResellShowCodeViewDelegate implements ItemViewDelegate<String>  {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_resell;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return  !item.equals("iem0");
    }

    @Override
    public void convert(ViewHolder holder, String s, int position) {

    }
}