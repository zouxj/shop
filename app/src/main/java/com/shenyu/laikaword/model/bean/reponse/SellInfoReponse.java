package com.shenyu.laikaword.model.bean.reponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SellInfoReponse extends BaseReponse implements Serializable {

    /**
     * payload : {"goodsName":"移动充值卡100元","goodsImg":"http://image.playingbuy.net/goods/cards/big-yd-100.png","originPrice":"100.00","num":1,"codeList":["E76A0DE6DG7UI5GG"],"discountRange":["6.0","9.7"],"discountOptions":[{"value":"9.2","desc":"最快"},{"value":"9.5","desc":"标准"},{"value":"9.7","desc":"收益最高"}],"default":"9.5","price":95,"serviceFeeRatio":0,"serviceFee":0}
     */

    private PayloadBean payload;

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public static class PayloadBean  implements Serializable {
        /**
         * goodsName : 移动充值卡100元
         * goodsImg : http://image.playingbuy.net/goods/cards/big-yd-100.png
         * originPrice : 100.00
         * num : 1
         * codeList : ["E76A0DE6DG7UI5GG"]
         * discountRange : ["6.0","9.7"]
         * discountOptions : [{"value":"9.2","desc":"最快"},{"value":"9.5","desc":"标准"},{"value":"9.7","desc":"收益最高"}]
         * default : 9.5
         * price : 95
         * serviceFeeRatio : 0
         * serviceFee : 0
         */

        private String goodsName;
        private String goodsImg;
        private String originPrice;
        private int num;
        @SerializedName("default")
        private String defaultX;
        private int price;
        private int serviceFeeRatio;
        private int serviceFee;
        private List<String> codeList;
        private List<String> discountRange;
        private List<DiscountOptionsBean> discountOptions;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsImg(String goodsImg) {
            this.goodsImg = goodsImg;
        }

        public String getOriginPrice() {
            return originPrice;
        }

        public void setOriginPrice(String originPrice) {
            this.originPrice = originPrice;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getServiceFeeRatio() {
            return serviceFeeRatio;
        }

        public void setServiceFeeRatio(int serviceFeeRatio) {
            this.serviceFeeRatio = serviceFeeRatio;
        }

        public int getServiceFee() {
            return serviceFee;
        }

        public void setServiceFee(int serviceFee) {
            this.serviceFee = serviceFee;
        }

        public List<String> getCodeList() {
            return codeList;
        }

        public void setCodeList(List<String> codeList) {
            this.codeList = codeList;
        }

        public List<String> getDiscountRange() {
            return discountRange;
        }

        public void setDiscountRange(List<String> discountRange) {
            this.discountRange = discountRange;
        }

        public List<DiscountOptionsBean> getDiscountOptions() {
            return discountOptions;
        }

        public void setDiscountOptions(List<DiscountOptionsBean> discountOptions) {
            this.discountOptions = discountOptions;
        }

        public static class DiscountOptionsBean  implements Serializable  {
            /**
             * value : 9.2
             * desc : 最快
             */

            private String value;
            private String desc;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
