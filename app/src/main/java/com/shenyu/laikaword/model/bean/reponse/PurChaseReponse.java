package com.shenyu.laikaword.model.bean.reponse;

import java.util.List;

public class PurChaseReponse extends BaseReponse {


    private List<PayloadBean> payload;

    public List<PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * type : quanbu
         * name : 全部
         * list : [{"extractId":"1803166435178256","extractNo":"E1803166435178256","packageId":"263","userId":"279","nickName":"132****4341","bindPhone":"13266834341","status":"4","type":"yd","goodsName":"移动充值卡50元","goodsImage":"http://image.playingbuy.net/goods/cards/small-yd-50.png","goodsValue":"50.00","quantity":"1","amount":"50.00","phone":"13266834341","name":null,"address":"","expressCompany":"","expressId":"1QM1521193951932","sendTime":"0","createTime":"1521193951","updateTime":"1521193951","userName":"lk0865_1151548933","state":2,"statusText":"已充值","stateDes":"已充值"},{"extractId":"1803166433369241","extractNo":"E1803166433369241","packageId":"262","userId":"279","nickName":"132****4341","bindPhone":"13266834341","status":"0","type":"jd","goodsName":"京东实体购物卡2000元","goodsImage":"http://image.playingbuy.net/goods/cards/small-jd-2000.png","goodsValue":"2000.00","quantity":"1","amount":"2000.00","phone":"13266834341","name":"邹修俊","address":"北京市东城区科技园","expressCompany":"","expressId":"","sendTime":"0","createTime":"1521193933","updateTime":"1521193933","userName":"lk0865_1151548933","state":1,"statusText":"待审核","stateDes":"发货中"}]
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

        public static class ListBean {
            /**
             * extractId : 1803166435178256
             * extractNo : E1803166435178256
             * packageId : 263
             * userId : 279
             * nickName : 132****4341
             * bindPhone : 13266834341
             * status : 4
             * type : yd
             * goodsName : 移动充值卡50元
             * goodsImage : http://image.playingbuy.net/goods/cards/small-yd-50.png
             * goodsValue : 50.00
             * quantity : 1
             * amount : 50.00
             * phone : 13266834341
             * name : null
             * address :
             * expressCompany :
             * expressId : 1QM1521193951932
             * sendTime : 0
             * createTime : 1521193951
             * updateTime : 1521193951
             * userName : lk0865_1151548933
             * state : 2
             * statusText : 已充值
             * stateDes : 已充值
             */

            private String extractId;
            private String extractNo;
            private String packageId;
            private String userId;
            private String nickName;
            private String bindPhone;
            private String status;
            private String pickupMethodId;
            private String type;
            private String goodsName;
            private String goodsImage;
            private String goodsValue;
            private String quantity;
            private String amount;
            private String phone;
            private Object name;
            private String address;
            private String expressCompany;
            private String expressId;
            private String sendTime;
            private String createTime;
            private String updateTime;
            private String userName;
            private int state;
            private String statusText;
            private String stateDes;

            public String getExtractId() {
                return extractId;
            }

            public void setExtractId(String extractId) {
                this.extractId = extractId;
            }

            public String getPickupMethodId() {
                return pickupMethodId;
            }

            public void setPickupMethodId(String pickupMethodId) {
                this.pickupMethodId = pickupMethodId;
            }

            public String getExtractNo() {
                return extractNo;
            }

            public void setExtractNo(String extractNo) {
                this.extractNo = extractNo;
            }

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

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getBindPhone() {
                return bindPhone;
            }

            public void setBindPhone(String bindPhone) {
                this.bindPhone = bindPhone;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getExpressCompany() {
                return expressCompany;
            }

            public void setExpressCompany(String expressCompany) {
                this.expressCompany = expressCompany;
            }

            public String getExpressId() {
                return expressId;
            }

            public void setExpressId(String expressId) {
                this.expressId = expressId;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
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

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getStatusText() {
                return statusText;
            }

            public void setStatusText(String statusText) {
                this.statusText = statusText;
            }

            public String getStateDes() {
                return stateDes;
            }

            public void setStateDes(String stateDes) {
                this.stateDes = stateDes;
            }
        }
    }
}
