package com.shenyu.laikaword.model.bean.reponse;

import android.annotation.SuppressLint;
import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public class LoginReponse extends BaseReponse implements Serializable {


    /**
     * payload : {"userId":"13","nickname":"132****4341","avatar":null,"bindPhone":"13266834341","bindWeChat":null,"weChatUnionId":null,"bindQQ":null,"qqOpenId":null,"createTime":"1506649774","isSetTransactionPIN":0,"token":"a81dfr3umpw2duzm1506649774"}
     */


private PayloadBean payload;

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    @SuppressLint("ParcelCreator")
    public static class PayloadBean implements Serializable {
        /**
         * userId : 13
         * nickname : 132****4341
         * avatar : null
         * bindPhone : 13266834341
         * bindWeChat : null
         * weChatUnionId : null
         * bindQQ : null
         * qqOpenId : null
         * createTime : 1506649774
         * isSetTransactionPIN : 0
         * token : a81dfr3umpw2duzm1506649774
         *  "money": "100",  //用户余额
         */

        private String userId;
        private String nickname;
        private String avatar;
        private String bindPhone;
        private String bindWeChat;
        private String weChatUnionId;
        private String bindQQ;
        private String qqOpenId;
        private String createTime;
        private int isSetTransactionPIN;
        private String token;
        private String money;
        private String bankCardNum;
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMoney() {
            return money;
        }

        public String getBankCardNum() {
            return bankCardNum;
        }

        public void setBankCardNum(String bankCardNum) {
            this.bankCardNum = bankCardNum;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBindPhone() {
            return bindPhone;
        }

        public void setBindPhone(String bindPhone) {
            this.bindPhone = bindPhone;
        }

        public String getBindWeChat() {
            return bindWeChat;
        }

        public void setBindWeChat(String bindWeChat) {
            this.bindWeChat = bindWeChat;
        }

        public Object getWeChatUnionId() {
            return weChatUnionId;
        }

        public void setWeChatUnionId(String weChatUnionId) {
            this.weChatUnionId = weChatUnionId;
        }

        public String getBindQQ() {
            return bindQQ;
        }

        public void setBindQQ(String bindQQ) {
            this.bindQQ = bindQQ;
        }

        public Object getQqOpenId() {
            return qqOpenId;
        }

        public void setQqOpenId(String qqOpenId) {
            this.qqOpenId = qqOpenId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getIsSetTransactionPIN() {
            return isSetTransactionPIN;
        }

        public void setIsSetTransactionPIN(int isSetTransactionPIN) {
            this.isSetTransactionPIN = isSetTransactionPIN;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }



        public PayloadBean() {
        }

        protected PayloadBean(Parcel in) {
            this.userId = in.readString();
            this.nickname = in.readString();
            this.avatar = in.readString();
            this.bindPhone = in.readString();
            this.bindWeChat = in.readString();
            this.weChatUnionId = in.readString();
            this.bindQQ = in.readString();
            this.qqOpenId = in.readString();
            this.createTime = in.readString();
            this.isSetTransactionPIN = in.readInt();
            this.token = in.readString();
            this.money = in.readString();
        }

    }





}
