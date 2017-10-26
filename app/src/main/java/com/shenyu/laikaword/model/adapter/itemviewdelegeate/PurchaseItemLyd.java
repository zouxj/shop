package com.shenyu.laikaword.model.adapter.itemviewdelegeate;

import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.ViewHolder;
import com.shenyu.laikaword.model.bean.reponse.PickUpGoodsReponse;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.DateTimeUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/10/21 0021.
 */

public class PurchaseItemLyd implements ItemViewDelegate<PickUpGoodsReponse.PayloadBean>
{
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_purchase;
    }

    @Override
    public boolean isForViewType(PickUpGoodsReponse.PayloadBean item, int position) {
        boolean bool=false;
        if (null!=item){
            if (StringUtil.validText(item.getType()))
                if (!item.getType().equals("jd"))
                    bool=true;
        }
        return bool;

    }

    @Override
    public void convert(ViewHolder holder, PickUpGoodsReponse.PayloadBean payloadBean, int position) {
        holder.setText(R.id.tv_purchase_indent_no,payloadBean.getPhone());
        holder.setText(R.id.tv_purchase_time, DateTimeUtil.formatDate( Long.parseLong(payloadBean.getCreateTime()),"yyyy-MM-dd HH:mm:ss"));
        holder.setText(R.id.tv_purchase_shop_name,payloadBean.getGoodsName());
        holder.setText(R.id.tv_purchase_countmun,"X"+payloadBean.getQuantity());
        holder.setText(R.id.tv_purchase_number,"编号:"+payloadBean.getExtractId());
        Picasso.with(UIUtil.getContext()).load(payloadBean.getGoodsImage()).placeholder(R.mipmap.defaul_icon).error(R.mipmap.defaul_icon).into((ImageView) holder.getView(R.id.img_purchase_img));
                    holder.setText(R.id.tv_purchase_count,"合计:"+(Double.parseDouble(payloadBean.getGoodsValue())*StringUtil.formatIntger(payloadBean.getQuantity())));


    }
}