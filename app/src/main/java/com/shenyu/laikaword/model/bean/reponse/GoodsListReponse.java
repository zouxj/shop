package com.shenyu.laikaword.model.bean.reponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/11/14 0014.
 */

public class GoodsListReponse extends BaseReponse  {

    /**
     * success : true
     * payload : [{"goodsId":"58","goodsType":"jd","goodsName":"京东实体购物卡50元","goodsBigImage":"http://image.comingcard.com/goods/cards/big-jd-50.png","goodsImage":"http://image.comingcard.com/goods/cards/small-jd-50.png","sendUserId":"761","nickName":"﹡藍紫 ﹠冰楓﹡","sellerAvatar":"http://wx.qlogo.cn/mmopen/vi_32/rictRAguOfC38siadp0LPr12ngtnRxV6kGhUGElwbkJw9A6svGQ9Z67ibpMOAxcXPEVdu5o6T0tl68cGw3uOzTw2w/0","discount":"98","originPrice":"50.00","discountPrice":"49.00","stock":"2","lockStock":"0","status":"0","createTime":"1509178354","offTime":"0","updateTime":"1510560721"},{"goodsId":"66","goodsType":"jd","goodsName":"京东实体购物卡50元","goodsBigImage":"http://image.comingcard.com/goods/cards/big-jd-50.png","goodsImage":"http://image.comingcard.com/goods/cards/small-jd-50.png","sendUserId":"788","nickName":"186****9175","sellerAvatar":"https://image.comingcard.com/avatar/167.jpg","discount":"98","originPrice":"50.00","discountPrice":"49.00","stock":"10","lockStock":"0","status":"0","createTime":"1509183950","offTime":"0","updateTime":"1510560481"},{"goodsId":"73","goodsType":"jd","goodsName":"京东实体购物卡100元","goodsBigImage":"http://image.comingcard.com/goods/cards/big-jd-100.png","goodsImage":"http://image.comingcard.com/goods/cards/small-jd-100.png","sendUserId":"758","nickName":"斯文","sellerAvatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJTicWezKMjQw1friatURSeTf08XlubjQN83HHDHh4XCIngTB3gySXywFrGibiaVzaVaRTHp8boIzoEhg/0","discount":"95","originPrice":"100.00","discountPrice":"95.00","stock":"4","lockStock":"0","status":"0","createTime":"1509251051","offTime":"0","updateTime":"1510210921"},{"goodsId":"74","goodsType":"jd","goodsName":"京东实体购物卡100元","goodsBigImage":"http://image.comingcard.com/goods/cards/big-jd-100.png","goodsImage":"http://image.comingcard.com/goods/cards/small-jd-100.png","sendUserId":"758","nickName":"斯文","sellerAvatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJTicWezKMjQw1friatURSeTf08XlubjQN83HHDHh4XCIngTB3gySXywFrGibiaVzaVaRTHp8boIzoEhg/0","discount":"95","originPrice":"100.00","discountPrice":"95.00","stock":"10","lockStock":"0","status":"0","createTime":"1509251051","offTime":"0","updateTime":"0"},{"goodsId":"75","goodsType":"jd","goodsName":"京东实体购物卡100元","goodsBigImage":"http://image.comingcard.com/goods/cards/big-jd-100.png","goodsImage":"http://image.comingcard.com/goods/cards/small-jd-100.png","sendUserId":"758","nickName":"斯文","sellerAvatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJTicWezKMjQw1friatURSeTf08XlubjQN83HHDHh4XCIngTB3gySXywFrGibiaVzaVaRTHp8boIzoEhg/0","discount":"95","originPrice":"100.00","discountPrice":"95.00","stock":"2","lockStock":"0","status":"0","createTime":"1509252174","offTime":"0","updateTime":"1510555374"},{"goodsId":"87","goodsType":"jd","goodsName":"京东实体购物卡100元","goodsBigImage":"http://image.comingcard.com/goods/cards/big-jd-100.png","goodsImage":"http://image.comingcard.com/goods/cards/small-jd-100.png","sendUserId":"445","nickName":"书神","sellerAvatar":"http://wx.qlogo.cn/mmopen/46hMKwhRaviaKywTCgFbBUMmWK8EKtIvEbSeAsCfG84UEiaicvA5sQUUcr9JQ56DDuV3GV3icadvZ2mZ5SaWSu5anWzicfZj3j0bR/0","discount":"92","originPrice":"100.00","discountPrice":"92.00","stock":"1","lockStock":"0","status":"0","createTime":"1509440488","offTime":"0","updateTime":"1510555561"}]
     */

    private List<PayloadBean> payload;



    public List<PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * goodsId : 58
         * goodsType : jd
         * goodsName : 京东实体购物卡50元
         * goodsBigImage : http://image.comingcard.com/goods/cards/big-jd-50.png
         * goodsImage : http://image.comingcard.com/goods/cards/small-jd-50.png
         * sendUserId : 761
         * nickName : ﹡藍紫 ﹠冰楓﹡
         * sellerAvatar : http://wx.qlogo.cn/mmopen/vi_32/rictRAguOfC38siadp0LPr12ngtnRxV6kGhUGElwbkJw9A6svGQ9Z67ibpMOAxcXPEVdu5o6T0tl68cGw3uOzTw2w/0
         * discount : 98
         * originPrice : 50.00
         * discountPrice : 49.00
         * stock : 2
         * lockStock : 0
         * status : 0
         * createTime : 1509178354
         * offTime : 0
         * updateTime : 1510560721
         */


        private String goodsId;
        private String goodsType;
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
        private String lockStock;
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

        public String getLockStock() {
            return lockStock;
        }

        public void setLockStock(String lockStock) {
            this.lockStock = lockStock;
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
