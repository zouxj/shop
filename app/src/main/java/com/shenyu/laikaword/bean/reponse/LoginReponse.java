package com.shenyu.laikaword.bean.reponse;

import com.shenyu.laikaword.bean.BaseReponse;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public class LoginReponse extends BaseReponse{
    private PayloadBean payload;
    public PayloadBean getPayload() {
        return payload;
    }
    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * token : 4s3xe4t37r28uib11505886035
         * avatar : http.comingcard.com/avatar/202.jpg
         * nickname : Coder
         * isSetTransactionPIN : false
         * bindWeChat :
         * bindQQ :
         * bindPhone : 123123123
         */
        private String token;
        private String avatar;
        private String nickname;
        private boolean isSetTransactionPIN;
        private String bindWeChat;
        private String bindQQ;
        private String bindPhone;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isIsSetTransactionPIN() {
            return isSetTransactionPIN;
        }

        public void setIsSetTransactionPIN(boolean isSetTransactionPIN) {
            this.isSetTransactionPIN = isSetTransactionPIN;
        }

        public String getBindWeChat() {
            return bindWeChat;
        }

        public void setBindWeChat(String bindWeChat) {
            this.bindWeChat = bindWeChat;
        }

        public String getBindQQ() {
            return bindQQ;
        }

        public void setBindQQ(String bindQQ) {
            this.bindQQ = bindQQ;
        }

        public String getBindPhone() {
            return bindPhone;
        }

        public void setBindPhone(String bindPhone) {
            this.bindPhone = bindPhone;
        }
    }


}
