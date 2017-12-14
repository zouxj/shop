package com.shenyu.laikaword.model.bean.reponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/12/14 0014.
 */

public class ZhuanMaiReponse extends BaseReponse {

    private List<PayloadBean> payload;

    public List<PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * goodsId : 446
         * goodsType : yd
         * resellGoodsId : 8
         * resellUserId : 1147
         * resellId : 298
         * goodsName : 移动充值卡50元
         * goodsBigImage : http://image.comingcard.com/goods/cards/big-yd-50.png
         * goodsImage : http://image.comingcard.com/goods/cards/small-yd-50.png
         * sendUserId : 156
         * nickName : 132****4341
         * sellerAvatar : http://app-upload-img-test.oss-cn-shanghai.aliyuncs.com/a-1510813458587-700000.png
         * discount : 98
         * originPrice : 50.00
         * discountPrice : 49.00
         * stock : 50
         * oriStock : 50
         * lockStock : 0
         * recoveryNum : 0
         * status : 0
         * createTime : 1513218377
         * offTime : 0
         * updateTime : 1513218377
         */

        private String goodsId;
        private String goodsType;
        private String resellGoodsId;
        private String resellUserId;
        private String resellId;
        private String goodsName;
        private String goodsBigImage;
        private String goodsImage;
        private String sendUserId;
        private String nickName;
        private String sellerAvatar;
        private String discount;
        private String originPrice;
        private String discountPrice;
        private String stock;
        private String oriStock;
        private String lockStock;
        private String recoveryNum;
        private String status;
        private String createTime;
        private String offTime;
        private String updateTime;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public String getResellGoodsId() {
            return resellGoodsId;
        }

        public void setResellGoodsId(String resellGoodsId) {
            this.resellGoodsId = resellGoodsId;
        }

        public String getResellUserId() {
            return resellUserId;
        }

        public void setResellUserId(String resellUserId) {
            this.resellUserId = resellUserId;
        }

        public String getResellId() {
            return resellId;
        }

        public void setResellId(String resellId) {
            this.resellId = resellId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsBigImage() {
            return goodsBigImage;
        }

        public void setGoodsBigImage(String goodsBigImage) {
            this.goodsBigImage = goodsBigImage;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public String getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(String sendUserId) {
            this.sendUserId = sendUserId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSellerAvatar() {
            return sellerAvatar;
        }

        public void setSellerAvatar(String sellerAvatar) {
            this.sellerAvatar = sellerAvatar;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getOriginPrice() {
            return originPrice;
        }

        public void setOriginPrice(String originPrice) {
            this.originPrice = originPrice;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getOriStock() {
            return oriStock;
        }

        public void setOriStock(String oriStock) {
            this.oriStock = oriStock;
        }

        public String getLockStock() {
            return lockStock;
        }

        public void setLockStock(String lockStock) {
            this.lockStock = lockStock;
        }

        public String getRecoveryNum() {
            return recoveryNum;
        }

        public void setRecoveryNum(String recoveryNum) {
            this.recoveryNum = recoveryNum;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOffTime() {
            return offTime;
        }

        public void setOffTime(String offTime) {
            this.offTime = offTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
