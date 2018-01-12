package com.shenyu.laikaword.model.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.bean.reponse.BankInfoReponse;
import com.shenyu.laikaword.model.holder.BaseViewHolder;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/12/14 0014.
 */

public class ReslerAdapter extends CommonAdapter<BankInfoReponse.PayloadBean> {
    public ReslerAdapter(int layoutId, List<BankInfoReponse.PayloadBean> datas) {
        super(layoutId, datas);
    }
//    private Context mContext;
//    private LayoutInflater mInflater;
//    private List<BankInfoReponse.PayloadBean> payload=new ArrayList<>();

//    public ReslerAdapter(Context context, List<BankInfoReponse.PayloadBean> payload) {
//        mContext = context;
//        mInflater = LayoutInflater.from(context);
//       this.payload=payload;
//    }




    @Override
    protected void convert(ViewHolder viewHolder, BankInfoReponse.PayloadBean payloadBean, int position) {

    }
}