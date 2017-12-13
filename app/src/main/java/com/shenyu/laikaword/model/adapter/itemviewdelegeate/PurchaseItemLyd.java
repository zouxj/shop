package com.shenyu.laikaword.model.adapter.itemviewdelegeate;

import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.ViewHolder;
import com.shenyu.laikaword.model.bean.reponse.PickUpGoodsReponse;
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
        holder.setText(R.id.tv_purchase_time, DateTimeUtil.formatDate(Long.parseLong(payloadBean.getCreateTime()),"yyyy-MM-dd HH:mm:ss"));
        holder.setText(R.id.tv_zhuanmai_shop_name,payloadBean.getGoodsName());
        holder.setText(R.id.tv_zhuamai_price,"X"+payloadBean.getQuantity());
        holder.setText(R.id.zhuanmai_date,"编号:"+payloadBean.getExtractId());
        ImageUitls.loadImg(payloadBean.getGoodsImage(),(ImageView) holder.getView(R.id.img_purchase_img));holder.setText(R.id.tv_purchase_count,"合计:"+(Double.parseDouble(payloadBean.getGoodsValue())*StringUtil.formatIntger(payloadBean.getQuantity())));
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
        textView.setText(PickUpGoodsReponse.StatusHuF.getName(index));

    }
}