package com.shenyu.laikaword.model.bean.reponse;

import com.shenyu.laikaword.base.BaseReponse;

/**
 * Created by shenyu_zxjCode on 2017/10/18 0018.
 */

public class PayInfoReponse extends BaseReponse {

    /**
     * payload : {"payInfo":"alipay_sdk=alipay-sdk-php-20161101&amp;app_id=2016081600255341&amp;biz_content=%7B%22subject%22%3A%22%5Cu7528%5Cu6237%5Cu5145%5Cu503c%22%2C%22out_trade_no%22%3A5391511423%2C%22total_amount%22%3A10%2C%22timeout_express%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=alipay.trade.app.pay&amp;sign_type=RSA2&amp;timestamp=2017-10-18+14%3A58%3A35&amp;version=1.0&amp;sign=sblEpoMJEl3ZNAqX6LmLk08teOzk0latj1tRY5fuwQ4hE31ufxjl3hxXu7H9sme1SnmgXPHf9Xywu41Sr33rScFLj2j7uWa6k8GAw88PvbkO6AAz7eJKuGmPMrRcPVBMNNnFPtqx%2FkZc8W3GoGDCCc3Mf88pG%2B0E5zaoA0hfpylfAcqsfh76oBasG5FCFAz3aIFgo25w%2Fr8ynH120paewDcOhzh%2B2xX5O2Whfs1mm9Cq8DX0s%2BGMSS5TBFhOWD5EwoZ4%2FRx9lQEyc8jet5PUbe0dDgzGk%2FvIfresdT%2BMBl8Ynos5tHNrqktqRQFZdEr0a2G%2B07sbM6QeLHkCYoq29w%3D%3D"}
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
         * payInfo : alipay_sdk=alipay-sdk-php-20161101&amp;app_id=2016081600255341&amp;biz_content=%7B%22subject%22%3A%22%5Cu7528%5Cu6237%5Cu5145%5Cu503c%22%2C%22out_trade_no%22%3A5391511423%2C%22total_amount%22%3A10%2C%22timeout_express%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=alipay.trade.app.pay&amp;sign_type=RSA2&amp;timestamp=2017-10-18+14%3A58%3A35&amp;version=1.0&amp;sign=sblEpoMJEl3ZNAqX6LmLk08teOzk0latj1tRY5fuwQ4hE31ufxjl3hxXu7H9sme1SnmgXPHf9Xywu41Sr33rScFLj2j7uWa6k8GAw88PvbkO6AAz7eJKuGmPMrRcPVBMNNnFPtqx%2FkZc8W3GoGDCCc3Mf88pG%2B0E5zaoA0hfpylfAcqsfh76oBasG5FCFAz3aIFgo25w%2Fr8ynH120paewDcOhzh%2B2xX5O2Whfs1mm9Cq8DX0s%2BGMSS5TBFhOWD5EwoZ4%2FRx9lQEyc8jet5PUbe0dDgzGk%2FvIfresdT%2BMBl8Ynos5tHNrqktqRQFZdEr0a2G%2B07sbM6QeLHkCYoq29w%3D%3D
         */

        private String payInfo;

        public String getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(String payInfo) {
            this.payInfo = payInfo;
        }
    }
}
