package com.shenyu.laikaword.bean.reponse;

import com.google.gson.annotations.SerializedName;
import com.shenyu.laikaword.bean.BaseReponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/9/30 0030.
 */

public class AddressReponse extends BaseReponse {

    private List<PayloadBean> payload;

    public List<PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * addressId : 1
         * userId : 7
         * receiveName : 唐22
         * phone : 18124005171
         * province : 广东
         * city : 深圳
         * district : 南山
         * detail : 泰邦科技大厦15k
         * createTime : 1506569454
         * default : 0
         */

        private String addressId;
        private String userId;
        private String receiveName;
        private String phone;
        private String province;
        private String city;
        private String district;
        private String detail;
        private String createTime;
        @SerializedName("default")
        private String defaultX;

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
        }
    }
}
