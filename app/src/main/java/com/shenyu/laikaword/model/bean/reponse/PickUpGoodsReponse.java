package com.shenyu.laikaword.model.bean.reponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/21 0021.
 */

public class PickUpGoodsReponse extends BaseReponse {

    private  List<PurChaseReponse.PayloadBean.ListBean>  payload;

    public  List<PurChaseReponse.PayloadBean.ListBean>  getPayload() {
        return payload;
    }

    public void setPayload(List<PurChaseReponse.PayloadBean.ListBean> payload) {
        this.payload = payload;
    }

}
