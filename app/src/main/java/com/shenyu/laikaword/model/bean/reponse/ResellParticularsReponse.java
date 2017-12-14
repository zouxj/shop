package com.shenyu.laikaword.model.bean.reponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/12/14 0014.
 */

public class ResellParticularsReponse extends BaseReponse {

    /**
     * payload : {"goodsId":"446","goodsType":"yd","resellGoodsId":"8","resellUserId":"1147","resellId":"298","goodsName":"移动充值卡50元","goodsBigImage":"http://image.comingcard.com/goods/cards/big-yd-50.png","goodsImage":"http://image.comingcard.com/goods/cards/small-yd-50.png","sendUserId":"156","nickName":"132****4341","sellerAvatar":"http://app-upload-img-test.oss-cn-shanghai.aliyuncs.com/a-1510813458587-700000.png","discount":"98","originPrice":"50.00","discountPrice":"49.00","stock":"0","oriStock":"50","lockStock":"0","recoveryNum":"50","status":"0","createTime":"1513218377","offTime":"1513218721","updateTime":"1513218721","allCodeList":["9E6207XBMV5WFULS","9E6207QCTOFQLXGO","9E6207O68XFUIA11","9E62072RLZ7UZZM4","9E62073LT8H6BK82","9E62074352ZLMIAQ","9E62072Z1SF1YRZM","9E6207OSMFYT4VJV","9E6207IKUJAIFCWB","9E6207IQGF286XI3","9E6207MVWZXWWMIE","9E6207IXJPZPO4ED","9E6207GCU3QTW1HA","9E6207PNUYA8Z6TK","9E6207JDR67OOTLV","9E620715NO3EQKZS","9E62074WDUWHB3ZB","9E6207439ZDMA5AN","9E6207SEX8BE1BMI","9E6207U3RMBL6AOI","C7C03CM15CVBS5FH","C7C03C6ZANV8HRZT","C7C03CILCGJN2RP4","C7C03CPJITYL6PIE","C7C03C77WBIYF9WE","C7C03CJVX49SH4AE","C7C03CNO5IMBF17P","C7C03CG531XPZL1G","C7C03CYC8YMOYS6I","C7C03C9MSVZZT11M","C7C03CTI58BF6W7F","C7C03CP2L9OHRAHN","C7C03C5CWQERJV6W","C7C03CBLI7GLF9RI","C7C03CTOV14G5BTK","C7C03CW1IC81PW16","C7C03CMS5FYJCMOG","C7C03C8U1554H9WQ","C7C03CGG2PXP5OV2","C7C03CKCZP9AD89G","73E0BFRM9YKHWH5L","73E0BFT77ST4A1XI","73E0BFXSPUTKMR4P","73E0BFR3LKLIOPY2","73E0BF3DX3S5CCFX","73E0BFEPFEZS6YVV","73E0BFL4OFCV2TPJ","73E0BF4YEV4D7NGP","73E0BFBDWBHH8OZW","73E0BF22YWJR21Q2"],"soldCodeList":["9E6207XBMV5WFULS","9E6207QCTOFQLXGO","9E6207O68XFUIA11","9E62072RLZ7UZZM4","9E62073LT8H6BK82","9E62074352ZLMIAQ","9E62072Z1SF1YRZM","9E6207OSMFYT4VJV","9E6207IKUJAIFCWB","9E6207IQGF286XI3","9E6207MVWZXWWMIE","9E6207IXJPZPO4ED","9E6207GCU3QTW1HA","9E6207PNUYA8Z6TK","9E6207JDR67OOTLV","9E620715NO3EQKZS","9E62074WDUWHB3ZB","9E6207439ZDMA5AN","9E6207SEX8BE1BMI","9E6207U3RMBL6AOI","C7C03CM15CVBS5FH","C7C03C6ZANV8HRZT","C7C03CILCGJN2RP4","C7C03CPJITYL6PIE","C7C03C77WBIYF9WE","C7C03CJVX49SH4AE","C7C03CNO5IMBF17P","C7C03CG531XPZL1G","C7C03CYC8YMOYS6I","C7C03C9MSVZZT11M","C7C03CTI58BF6W7F","C7C03CP2L9OHRAHN","C7C03C5CWQERJV6W","C7C03CBLI7GLF9RI","C7C03CTOV14G5BTK","C7C03CW1IC81PW16","C7C03CMS5FYJCMOG","C7C03C8U1554H9WQ","C7C03CGG2PXP5OV2","C7C03CKCZP9AD89G","73E0BFRM9YKHWH5L","73E0BFT77ST4A1XI","73E0BFXSPUTKMR4P","73E0BFR3LKLIOPY2","73E0BF3DX3S5CCFX","73E0BFEPFEZS6YVV","73E0BFL4OFCV2TPJ","73E0BF4YEV4D7NGP","73E0BFBDWBHH8OZW","73E0BF22YWJR21Q2"]}
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
         * stock : 0
         * oriStock : 50
         * lockStock : 0
         * recoveryNum : 50
         * status : 0
         * createTime : 1513218377
         * offTime : 1513218721
         * updateTime : 1513218721
         * allCodeList : ["9E6207XBMV5WFULS","9E6207QCTOFQLXGO","9E6207O68XFUIA11","9E62072RLZ7UZZM4","9E62073LT8H6BK82","9E62074352ZLMIAQ","9E62072Z1SF1YRZM","9E6207OSMFYT4VJV","9E6207IKUJAIFCWB","9E6207IQGF286XI3","9E6207MVWZXWWMIE","9E6207IXJPZPO4ED","9E6207GCU3QTW1HA","9E6207PNUYA8Z6TK","9E6207JDR67OOTLV","9E620715NO3EQKZS","9E62074WDUWHB3ZB","9E6207439ZDMA5AN","9E6207SEX8BE1BMI","9E6207U3RMBL6AOI","C7C03CM15CVBS5FH","C7C03C6ZANV8HRZT","C7C03CILCGJN2RP4","C7C03CPJITYL6PIE","C7C03C77WBIYF9WE","C7C03CJVX49SH4AE","C7C03CNO5IMBF17P","C7C03CG531XPZL1G","C7C03CYC8YMOYS6I","C7C03C9MSVZZT11M","C7C03CTI58BF6W7F","C7C03CP2L9OHRAHN","C7C03C5CWQERJV6W","C7C03CBLI7GLF9RI","C7C03CTOV14G5BTK","C7C03CW1IC81PW16","C7C03CMS5FYJCMOG","C7C03C8U1554H9WQ","C7C03CGG2PXP5OV2","C7C03CKCZP9AD89G","73E0BFRM9YKHWH5L","73E0BFT77ST4A1XI","73E0BFXSPUTKMR4P","73E0BFR3LKLIOPY2","73E0BF3DX3S5CCFX","73E0BFEPFEZS6YVV","73E0BFL4OFCV2TPJ","73E0BF4YEV4D7NGP","73E0BFBDWBHH8OZW","73E0BF22YWJR21Q2"]
         * soldCodeList : ["9E6207XBMV5WFULS","9E6207QCTOFQLXGO","9E6207O68XFUIA11","9E62072RLZ7UZZM4","9E62073LT8H6BK82","9E62074352ZLMIAQ","9E62072Z1SF1YRZM","9E6207OSMFYT4VJV","9E6207IKUJAIFCWB","9E6207IQGF286XI3","9E6207MVWZXWWMIE","9E6207IXJPZPO4ED","9E6207GCU3QTW1HA","9E6207PNUYA8Z6TK","9E6207JDR67OOTLV","9E620715NO3EQKZS","9E62074WDUWHB3ZB","9E6207439ZDMA5AN","9E6207SEX8BE1BMI","9E6207U3RMBL6AOI","C7C03CM15CVBS5FH","C7C03C6ZANV8HRZT","C7C03CILCGJN2RP4","C7C03CPJITYL6PIE","C7C03C77WBIYF9WE","C7C03CJVX49SH4AE","C7C03CNO5IMBF17P","C7C03CG531XPZL1G","C7C03CYC8YMOYS6I","C7C03C9MSVZZT11M","C7C03CTI58BF6W7F","C7C03CP2L9OHRAHN","C7C03C5CWQERJV6W","C7C03CBLI7GLF9RI","C7C03CTOV14G5BTK","C7C03CW1IC81PW16","C7C03CMS5FYJCMOG","C7C03C8U1554H9WQ","C7C03CGG2PXP5OV2","C7C03CKCZP9AD89G","73E0BFRM9YKHWH5L","73E0BFT77ST4A1XI","73E0BFXSPUTKMR4P","73E0BFR3LKLIOPY2","73E0BF3DX3S5CCFX","73E0BFEPFEZS6YVV","73E0BFL4OFCV2TPJ","73E0BF4YEV4D7NGP","73E0BFBDWBHH8OZW","73E0BF22YWJR21Q2"]
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
        private List<String> allCodeList;
        private List<String> soldCodeList;

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

        public List<String> getAllCodeList() {
            return allCodeList;
        }

        public void setAllCodeList(List<String> allCodeList) {
            this.allCodeList = allCodeList;
        }

        public List<String> getSoldCodeList() {
            return soldCodeList;
        }

        public void setSoldCodeList(List<String> soldCodeList) {
            this.soldCodeList = soldCodeList;
        }
    }
}
