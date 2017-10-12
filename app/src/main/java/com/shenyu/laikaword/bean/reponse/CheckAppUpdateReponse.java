package com.shenyu.laikaword.bean.reponse;

import com.shenyu.laikaword.bean.BaseReponse;

/**
 * Created by shenyu_zxjCode on 2017/10/11 0011.
 */

public class CheckAppUpdateReponse extends BaseReponse {

    /**
     * payload : {"newVersion":"2.0.0","type":"1","message":"1.增加了XXX功能\n2.解决了XXX问题\n3.修复了XXXXBUG"}
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
         * newVersion : 2.0.0
         * type : 1
         * message : 1.增加了XXX功能
         2.解决了XXX问题
         3.修复了XXXXBUG
         */

        private String newVersion;
        private String type;
        private String message;
        private String downloadUrl;

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getNewVersion() {
            return newVersion;
        }

        public void setNewVersion(String newVersion) {
            this.newVersion = newVersion;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
