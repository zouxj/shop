package com.shenyu.laikaword.model.bean.reponse;

import com.shenyu.laikaword.base.BaseReponse;

/**
 * Created by shenyu_zxjCode on 2017/10/27 0027.
 */

public class StartBannerGuangKReponse extends BaseReponse {
    /**
     * payload : {"imageUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509102070891&di=9ae11a86c48edbbaaf39485f90246761&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F011995554b2b6f0000007cc2fd30e6.jpg","jumpUrl":"http://www.baidu.com"}
     */

    private PayloadBean payload;

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * imageUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509102070891&di=9ae11a86c48edbbaaf39485f90246761&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F011995554b2b6f0000007cc2fd30e6.jpg
         * jumpUrl : http://www.baidu.com
         */

        private String imageUrl;
        private String jumpUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }
    }
}
