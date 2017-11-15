package com.shenyu.laikaword.model.bean.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/9 0009.
 */

public class BankInfoReponse extends BaseReponse{

    public List<BankInfoReponse.PayloadBean> payload;

    public List<PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * cardId : 1
         * cardNo : 6222032104001330634
         * userId : 7
         * userName : 唐旺辰
         * bankName : 工商银行
         * bankDetail : 深圳分行南山支行
         * createTime : 1506569121
         * province : 广东
         * city : 深圳
         * default : 0
         */

        private String cardId;
        private String cardNo;
        private String userId;
        private String userName;
        private String bankName;
        private String bankDetail;
        private String createTime;
        private String province;
        private String city;
        @SerializedName("default")
        private String defaultX;

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankDetail() {
            return bankDetail;
        }

        public void setBankDetail(String bankDetail) {
            this.bankDetail = bankDetail;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
        }
    }
}
