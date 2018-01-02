package com.shenyu.laikaword.model.bean.reponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zxjCo on 2018/1/2.
 */

public class WeixinPayReponse extends BaseReponse {


    /**
     * payload : {"payInfo":{"appid":"wx40cd3c9868cf2b92","partnerid":"1490662262","prepayid":"wx20180102175413173c983d950267957234","noncestr":"u0P8qeTqmgxwp12T","timestamp":1514886853,"package":"Sign=WXPay","sign":"5B684FA4B441EF7567EB8F5B19B87E2C"}}
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
         * payInfo : {"appid":"wx40cd3c9868cf2b92","partnerid":"1490662262","prepayid":"wx20180102175413173c983d950267957234","noncestr":"u0P8qeTqmgxwp12T","timestamp":1514886853,"package":"Sign=WXPay","sign":"5B684FA4B441EF7567EB8F5B19B87E2C"}
         */

        private PayInfoBean payInfo;

        public PayInfoBean getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(PayInfoBean payInfo) {
            this.payInfo = payInfo;
        }

        public static class PayInfoBean {
            /**
             * appid : wx40cd3c9868cf2b92
             * partnerid : 1490662262
             * prepayid : wx20180102175413173c983d950267957234
             * noncestr : u0P8qeTqmgxwp12T
             * timestamp : 1514886853
             * package : Sign=WXPay
             * sign : 5B684FA4B441EF7567EB8F5B19B87E2C
             */

            private String appid;
            private String partnerid;
            private String prepayid;
            private String noncestr;
            private int timestamp;
            @SerializedName("package")
            private String packageX;
            private String sign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
