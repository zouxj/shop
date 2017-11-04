package com.shenyu.laikaword.model.adapter.itemviewdelegeate;

import android.view.View;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.ViewHolder;
import com.shenyu.laikaword.model.bean.reponse.PickUpGoodsReponse;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.DateTimeUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/10/21 0021.
 */

public class PurchaseItemJd  implements ItemViewDelegate<PickUpGoodsReponse.PayloadBean>
{
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_purchase_jd;
    }

    @Override
    public boolean isForViewType(PickUpGoodsReponse.PayloadBean item, int position) {
        boolean bool=false;
        if (null!=item){
            if (StringUtil.validText(item.getType()))
                if (item.getType().equals("jd"))
                    bool=true;
        }
        return bool;
    }

    @Override
    public void convert(ViewHolder holder, PickUpGoodsReponse.PayloadBean payloadBean, int position) {
        holder.setText(R.id.tv_purchase_indent_no,payloadBean.getName()+"  "+payloadBean.getPhone());
        holder.setText(R.id.tv_purchase_address,payloadBean.getAddress());
        if (StringUtil.validText(payloadBean.getExpressId())&&StringUtil.validText(payloadBean.getExpressCompany())) {
            holder.getView(R.id.tv_purchase_order_number).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_purchase_order_number, "快递单号:" + payloadBean.getExpressId() + "(" + payloadBean.getExpressCompany() + ")");
        }
        else
        holder.getView(R.id.tv_purchase_order_number).setVisibility(View.GONE);

        holder.setText(R.id.tv_purchase_time, DateTimeUtil.formatDate( Long.parseLong(payloadBean.getCreateTime()),"yyy-MM-dd HH:mm:ss"));
        holder.setText(R.id.tv_purchase_shop_name,payloadBean.getGoodsName());
        holder.setText(R.id.tv_purchase_countmun,"X"+payloadBean.getQuantity());
        holder.setText(R.id.tv_purchase_number,"编号:"+payloadBean.getExtractId());
        holder.setText(R.id.tv_jingd_order_number,"京东单号:" + payloadBean.getExpressId());
        ImageUitls.loadImg(payloadBean.getGoodsImage(),(ImageView) holder.getView(R.id.img_purchase_img));

    }
}
