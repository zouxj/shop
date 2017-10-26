package com.shenyu.laikaword.model.adapter.itemviewdelegeate;

import com.shenyu.laikaword.model.adapter.ViewHolder;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);
}
