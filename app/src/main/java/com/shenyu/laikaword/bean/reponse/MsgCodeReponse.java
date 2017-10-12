package com.shenyu.laikaword.bean.reponse;

import com.shenyu.laikaword.bean.BaseReponse;

/**
 * Created by shenyu_zxjCode on 2017/10/11 0011.
 */

public class MsgCodeReponse extends BaseReponse {
    /**
     * payload : {"SMSToken":"49prrotf5su1jegm"}
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
         * SMSToken : 49prrotf5su1jegm
         */

        private String SMSToken;

        public String getSMSToken() {
            return SMSToken;
        }

        public void setSMSToken(String SMSToken) {
            this.SMSToken = SMSToken;
        }
    }
}
