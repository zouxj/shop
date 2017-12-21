package com.shenyu.laikaword.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.bean.reponse.BankInfoReponse;
import com.zxj.utilslibrary.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/12/14 0014.
 */

public class ReslerAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<BankInfoReponse.PayloadBean> payload=new ArrayList<>();

    public ReslerAdapter(Context context, List<BankInfoReponse.PayloadBean> payload) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
       this.payload=payload;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_cardinfo_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BaseViewHolder viewHolder = (BaseViewHolder) holder;
        viewHolder.setText(R.id.tv_bank_no,"尾号"+ StringUtil.getBankNumber(payload.get(position).getCardNo()));
        viewHolder.setText(R.id.tv_card_bank,payload.get(position).getBankName());
        ImageUitls.loadImgRound(payload.get(position).getBankLogo(), (ImageView) viewHolder.getView(R.id.iv_bank_log),R.mipmap.banklogo);
    }

    @Override
    public int getItemCount() {
        return payload != null ? payload.size() : 0;
    }

    public void removeItem(int position) {
        payload.remove(position);
    }
}