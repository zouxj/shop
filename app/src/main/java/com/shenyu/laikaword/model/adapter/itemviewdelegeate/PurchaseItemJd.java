package com.shenyu.laikaword.model.adapter.itemviewdelegeate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        int state=  StringUtil.formatIntger(payloadBean.getStatus());
        int index = 0;
        //提货状态，0:初始状态,1:已审核,2:审核不通过,3:发货中,4:已发货,5:发货失败,6:完成,7:关闭
        if (state==6||state==4){
            index=0;
        }else if (state==2||state==5||state==7){
            index=1;
        }else if (state==0||state==1||state==3){
            index=2;
        }
        TextView textView =holder.getView(R.id.tv_status);
        switch (index){
            case 0:
                textView.setTextColor(UIUtil.getColor(R.color.color_green));
                break;
            case 1:
                textView.setTextColor(UIUtil.getColor(R.color.app_theme_red));
                break;
            case 2:
                textView.setTextColor(UIUtil.getColor(R.color.color_yellow));
                break;
        }
        textView.setText(PickUpGoodsReponse.StatusJD.getName(index));
        holder.setText(R.id.tv_purchase_time, DateTimeUtil.formatDate( Long.parseLong(payloadBean.getCreateTime()),"yyy-MM-dd HH:mm:ss"));
        holder.setText(R.id.tv_purchase_shop_name,payloadBean.getGoodsName());
        holder.setText(R.id.tv_purchase_countmun,"X"+payloadBean.getQuantity());
        holder.setText(R.id.tv_purchase_number,"编号:"+payloadBean.getExtractId());
//        holder.setText(R.id.tv_jingd_order_number,"京东单号:" + payloadBean.getExpressId());
        ImageUitls.loadImg(payloadBean.getGoodsImage(),(ImageView) holder.getView(R.id.img_purchase_img));

    }
}
