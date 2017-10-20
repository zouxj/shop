package com.shenyu.laikaword.bean.reponse;

import com.shenyu.laikaword.bean.BaseReponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/17 0017.
 */

public class OrderListReponse extends BaseReponse{
    private List<PayloadBean> payload;

    public List<OrderListReponse.PayloadBean> getPayloads() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public  class PayloadBean {
        /**
         * orderId : 12345667890
         * goodsId : 1
         * status : 2
         * quantity : 10
         * amounts : 950.00
         * goodsName : 商品名称
         * goodsImage : http://image.comingcard.com/goods/yd100.png
         * originalPrice : 100
         * discountPrice : 95
         * discount : 9.5
         * createTime : 1508222676
         */

        private String orderId;
        private int goodsId;
        private int status;
        private int quantity;
        private String amounts;
        private String goodsName;
        private String goodsImage;
        private String originalPrice;
        private String discountPrice;
        private String discount;
        private int createTime;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getAmounts() {
            return amounts;
        }

        public void setAmounts(String amounts) {
            this.amounts = amounts;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }
    }
}
