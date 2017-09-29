package com.shenyu.laikaword.bean.reponse;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.shenyu.laikaword.bean.BaseReponse;

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
    public  class PayloadBean implements Serializable {
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

        public Object getBindWeChat() {
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

        public Object getBindQQ() {
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

    }


    public LoginReponse() {
    }

    protected LoginReponse(Parcel in) {
        this.payload = in.readParcelable(PayloadBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginReponse> CREATOR = new Parcelable.Creator<LoginReponse>() {
        @Override
        public LoginReponse createFromParcel(Parcel source) {
            return new LoginReponse(source);
        }

        @Override
        public LoginReponse[] newArray(int size) {
            return new LoginReponse[size];
        }
    };
}
