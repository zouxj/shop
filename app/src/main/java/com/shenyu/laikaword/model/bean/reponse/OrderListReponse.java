package com.shenyu.laikaword.model.bean.reponse;

import com.shenyu.laikaword.base.BaseReponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/17 0017.
 */

public class OrderListReponse extends BaseReponse{


    private List<PayloadBean> payload;

    public List<PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * orderId : 1710303716455154
         * orderNo : O1710303716455154
         * payStatus : 0
         * userId : 7
         * amount : 0.01
         * payWay : 5
         * payTime : null
         * exchangeSn :
         * createTime : 1509329964
         * goods : [{"orderGoodsId":"277","orderId":"1710303716455154","goodsId":"57","quantity":"10","goodsName":"京东购物卡100元","goodsImage":"http://app-upload-img-test.oss-cn-shanghai.aliyuncs.com/a-150918","originPrice":"100.00","currentPrice":"98.00","createTime":"1509329964"}]
         */

        private String orderId;
        private String orderNo;
        private String payStatus;
        private String userId;
        private String amount;
        private String payWay;
        private Object payTime;
        private String exchangeSn;
        private String createTime;
        private List<GoodsBean> goods;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public Object getPayTime() {
            return payTime;
        }

        public void setPayTime(Object payTime) {
            this.payTime = payTime;
        }

        public String getExchangeSn() {
            return exchangeSn;
        }

        public void setExchangeSn(String exchangeSn) {
            this.exchangeSn = exchangeSn;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * orderGoodsId : 277
             * orderId : 1710303716455154
             * goodsId : 57
             * quantity : 10
             * goodsName : 京东购物卡100元
             * goodsImage : http://app-upload-img-test.oss-cn-shanghai.aliyuncs.com/a-150918
             * originPrice : 100.00
             * currentPrice : 98.00
             * createTime : 1509329964
             */

            private String orderGoodsId;
            private String orderId;
            private String goodsId;
            private String quantity;
            private String goodsName;
            private String goodsImage;
            private String originPrice;
            private String currentPrice;
            private String createTime;

            public String getOrderGoodsId() {
                return orderGoodsId;
            }

            public void setOrderGoodsId(String orderGoodsId) {
                this.orderGoodsId = orderGoodsId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
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

            public String getOriginPrice() {
                return originPrice;
            }

            public void setOriginPrice(String originPrice) {
                this.originPrice = originPrice;
            }

            public String getCurrentPrice() {
                return currentPrice;
            }

            public void setCurrentPrice(String currentPrice) {
                this.currentPrice = currentPrice;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
