package com.shenyu.laikaword.model.bean.reponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/24 0024.
 */

public class MoneyDetailReponse extends BaseReponse {

    private List<PayloadBean> payload;

    public List<PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * recordId : 78
         * userId : 7
         * detail : 购买goods2
         * money : -0.01
         * createTime : 1508816917
         * type : 1
         */

        private String recordId;
        private String userId;
        private String detail;
        private String money;
        private String createTime;
        private String type;

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
