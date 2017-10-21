package com.shenyu.laikaword.adapter;

import com.shenyu.laikaword.adapter.itemviewdelegeate.PurchaseItemJd;
import com.shenyu.laikaword.adapter.itemviewdelegeate.PurchaseItemLyd;
import com.shenyu.laikaword.bean.reponse.PickUpGoodsReponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/21 0021.
 */

public class PurchaseItemAdapter extends MultiItemTypeAdapter<PickUpGoodsReponse.PayloadBean> {
    public PurchaseItemAdapter(List<PickUpGoodsReponse.PayloadBean> datas) {
        super(datas);
        addItemViewDelegate(new PurchaseItemJd());
        addItemViewDelegate(new PurchaseItemLyd());
    }
}
