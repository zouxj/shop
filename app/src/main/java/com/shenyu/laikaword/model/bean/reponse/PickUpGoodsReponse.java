package com.shenyu.laikaword.model.bean.reponse;

import com.shenyu.laikaword.R;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/21 0021.
 */

public class PickUpGoodsReponse extends BaseReponse {

    private List<PayloadBean> payload;

    public List<PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * extractId : 1710213610111093
         * extractNo : E1710213610111093
         * userId : 43
         * status : 0
         * type : jd
         * goodsValue : 100.00
         * quantity : 3
         * phone : 13266834341
         * name : 优秀罗
         * address : 北京市东城区xxxxxxx
         * expressId :
         * createTime : 1508551301
         * goodsName : goods1
         * goodsImage : http://image.comingcard.com/goods/yd100.png
         * expressCompany :
         * sendTime : null
         */

        private String extractId;
        private String extractNo;
        private String userId;
        private String status;
        private String type;
        private String goodsValue;
        private String quantity;
        private String phone;
        private String name;
        private String address;
        private String expressId;
        private String createTime;
        private String goodsName;
        private String goodsImage;
        private String expressCompany;
        private Object sendTime;

        public String getExtractId() {
            return extractId;
        }

        public void setExtractId(String extractId) {
            this.extractId = extractId;
        }

        public String getExtractNo() {
            return extractNo;
        }

        public void setExtractNo(String extractNo) {
            this.extractNo = extractNo;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStatus() {

            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGoodsValue() {
            return goodsValue;
        }

        public void setGoodsValue(String goodsValue) {
            this.goodsValue = goodsValue;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getExpressId() {
            return expressId;
        }

        public void setExpressId(String expressId) {
            this.expressId = expressId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public String getExpressCompany() {
            return expressCompany;
        }

        public void setExpressCompany(String expressCompany) {
            this.expressCompany = expressCompany;
        }

        public Object getSendTime() {
            return sendTime;
        }

        public void setSendTime(Object sendTime) {
            this.sendTime = sendTime;
        }
    }
    public  enum StatusHuF{
        //提货状态，0:初始状态,1:已审核,2:审核不通过,3:发货中,4:已发货,5:发货失败,6:完成,7:关闭
        INITIAL("充值成功", 0), CHECKED("充值失败", 1), PASS("充值中", 2);
        // 成员变量
        private String name;
        private int index;
        // 构造方法
        private StatusHuF(String name, int index) {
            this.name = name;
            this.index = index;
        }
        // 普通方法
        public static String getName(int index) {
            for (StatusHuF c : StatusHuF.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        public int getIndex() {
            return index;
        }
    }

    public  enum StatusJD{
        //提货状态，0:初始状态,1:已审核,2:审核不通过,3:发货中,4:已发货,5:发货失败,6:完成,7:关闭
        INITIAL("已发货", 0), CHECKED("提货失败", 1), PASS("待发货", 2);
        // 成员变量
        private String name;
        private int index;
        // 构造方法
        private StatusJD(String name, int index) {
            this.name = name;
            this.index = index;
        }
        // 普通方法
        public static String getName(int index) {
            for (StatusJD c : StatusJD.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        public int getIndex() {
            return index;
        }
        }
}
