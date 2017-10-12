package com.shenyu.laikaword.bean.reponse;

import com.shenyu.laikaword.bean.BaseReponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/12 0012.
 */

public class GoodsBean extends BaseReponse{
    private List<GoodBean> payload;

    public List<GoodBean> getPayload() {
        return payload;
    }

    public void setPayload(List<GoodBean> payload) {
        this.payload = payload;
    }

}
