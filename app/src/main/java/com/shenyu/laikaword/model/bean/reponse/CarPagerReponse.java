package com.shenyu.laikaword.model.bean.reponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/20 0020.
 */

public class CarPagerReponse extends BaseReponse implements Serializable{


    private List<PayloadBean> payload;

    public List<PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean implements Serializable{
        /**
         * type : jd
         * name : 京东卡
         * list : [{"packageId":"262","userId":"279","goodsName":"京东实体购物卡2000元","goodsImage":"http://image.playingbuy.net/goods/cards/small-jd-2000.png","goodsType":"jd","goodsValue":"2000.00","quantity":"1","createTime":"1521187383","updateTime":"0"},{"packageId":"215","userId":"279","goodsName":"京东实体购物卡100元","goodsImage":"http://image.playingbuy.com/goods/cards/small-jd-100.png","goodsType":"jd","goodsValue":"100.00","quantity":"7","createTime":"1517800903","updateTime":"0"}]
         */

        private String type;
        private String name;
        private List<ListBean> list;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * packageId : 262
             * userId : 279
             * goodsName : 京东实体购物卡2000元
             * goodsImage : http://image.playingbuy.net/goods/cards/small-jd-2000.png
             * goodsType : jd
             * goodsValue : 2000.00
             * quantity : 1
             * createTime : 1521187383
             * updateTime : 0
             */
            private String pickupMethodId;
            private String packageId;
            private String userId;
            private String goodsName;
            private String goodsImage;
            private String goodsType;
            private String goodsValue;
            private String quantity;
            private String createTime;
            private String updateTime;

            public String getPackageId() {
                return packageId;
            }

            public void setPackageId(String packageId) {
                this.packageId = packageId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getPickupMethodId() {
                return pickupMethodId;
            }

            public void setPickupMethodId(String pickupMethodId) {
                this.pickupMethodId = pickupMethodId;
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

            public String getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(String goodsType) {
                this.goodsType = goodsType;
            }

            public String getGoodsValue() {
                return goodsValue;
            }

            public void setGoodsValue(String goodsValue) {
                this.goodsValue = goodsValue;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
