package com.shenyu.laikaword.model.adapter;

import com.shenyu.laikaword.model.bean.reponse.PurChaseReponse;
import com.shenyu.laikaword.model.itemviewdelegeate.PurchaseItemJd;
import com.shenyu.laikaword.model.itemviewdelegeate.PurchaseItemLyd;
import com.shenyu.laikaword.model.bean.reponse.PickUpGoodsReponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/21 0021.
 */

public class PurchaseItemAdapter extends MultiItemTypeAdapter<PurChaseReponse.PayloadBean.ListBean> {
    public PurchaseItemAdapter(List<PurChaseReponse.PayloadBean.ListBean> datas) {
        super(datas);
        addItemViewDelegate(new PurchaseItemJd());
        addItemViewDelegate(new PurchaseItemLyd());
    }
}
