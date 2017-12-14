package com.shenyu.laikaword.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shenyu.laikaword.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/12/14 0014.
 */

public class ReslerAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mList;

    public ReslerAdapter(Context context, List<String> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_cardinfo_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BaseViewHolder viewHolder = (BaseViewHolder) holder;
        viewHolder.setText(R.id.item_content,mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }
}