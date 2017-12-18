package com.shenyu.laikaword.model.bean.reponse;

/**
 * Created by shenyu_zxjCode on 2017/12/16 0016.
 */

public class BankReponse extends BaseReponse {

    /**
     * payload : {"bankcard":"6222021901020600357","name":"E时代卡","province":"湖南","city":"长沙","type":"借记卡","len":"19","bank":"中国工商银行",
     * "bankno":"01020000","logo":"http://api.jisuapi.com/bankcard/upload/80/2.png","tel":"95588","website":"http://www.icbc.com.cn","iscorrect":"1"}
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
         * bankcard : 6222021901020600357
         * name : E时代卡
         * province : 湖南
         * city : 长沙
         * type : 借记卡
         * len : 19
         * bank : 中国工商银行
         * bankno : 01020000
         * logo : http://api.jisuapi.com/bankcard/upload/80/2.png
         * tel : 95588
         * website : http://www.icbc.com.cn
         * iscorrect : 1
         */

        private String bankcard;
        private String name;
        private String province;
        private String city;
        private String type;
        private String len;
        private String bank;
        private String bankno;
        private String logo;
        private String tel;
        private String website;
        private String iscorrect;
        private String nickName;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getBankcard() {
            return bankcard;
        }

        public void setBankcard(String bankcard) {
            this.bankcard = bankcard;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLen() {
            return len;
        }

        public void setLen(String len) {
            this.len = len;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBankno() {
            return bankno;
        }

        public void setBankno(String bankno) {
            this.bankno = bankno;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getIscorrect() {
            return iscorrect;
        }

        public void setIscorrect(String iscorrect) {
            this.iscorrect = iscorrect;
        }
    }
}

